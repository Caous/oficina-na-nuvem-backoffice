package com.oficinaapp.oficina_app.domain.support;

import java.util.Locale;

/**
 * Normalizes free text before it is compared or stored, so that lookups never
 * depend on how the caller typed the value.
 */
public final class TextNormalizer {

    private TextNormalizer() {
    }

    /** Lower cased and trimmed, the single form an e-mail is stored and searched by. */
    public static String email(String value) {
        return value == null ? null : value.trim().toLowerCase(Locale.ROOT);
    }

    /** Digits only, so that "123.456.789-00" and "12345678900" are the same document. */
    public static String digits(String value) {
        return value == null ? null : value.replaceAll("\\D", "");
    }
}
