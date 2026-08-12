package com.oficinaapp.oficina_app.infrastructure.security;

import com.oficinaapp.oficina_app.application.port.AccessToken;
import com.oficinaapp.oficina_app.application.port.AccessTokenIssuer;
import com.oficinaapp.oficina_app.domain.enums.UserRole;
import com.oficinaapp.oficina_app.domain.model.UserAccount;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Writes and reads the signed token. Both directions share one key and one set
 * of claim names, so they can never drift apart.
 */
@Component
public class JwtTokenCodec implements AccessTokenIssuer {

    private static final String CLAIM_EMAIL = "email";
    private static final String CLAIM_ROLE = "role";
    private static final String CLAIM_WORKSHOP_ID = "workshopId";

    private final JwtProperties properties;
    private final SecretKey signingKey;

    public JwtTokenCodec(JwtProperties properties) {
        this.properties = properties;
        this.signingKey = Keys.hmacShaKeyFor(properties.secret().getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public AccessToken issueFor(UserAccount account) {
        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plus(properties.expirationMinutes(), ChronoUnit.MINUTES);

        String value = Jwts.builder()
                .issuer(properties.issuer())
                .subject(String.valueOf(account.getId()))
                .claim(CLAIM_EMAIL, account.getEmail())
                .claim(CLAIM_ROLE, account.getRole().name())
                .claim(CLAIM_WORKSHOP_ID, account.getWorkshopId())
                .issuedAt(Date.from(issuedAt))
                .expiration(Date.from(expiresAt))
                .signWith(signingKey)
                .compact();

        return new AccessToken(value, expiresAt);
    }

    /**
     * @throws io.jsonwebtoken.JwtException when the token is forged, malformed
     *                                      or expired.
     */
    public AuthenticatedUserPrincipal readPrincipal(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(signingKey)
                .requireIssuer(properties.issuer())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return new AuthenticatedUserPrincipal(
                Long.valueOf(claims.getSubject()),
                claims.get(CLAIM_EMAIL, String.class),
                UserRole.valueOf(claims.get(CLAIM_ROLE, String.class)),
                claims.get(CLAIM_WORKSHOP_ID, Long.class)
        );
    }
}
