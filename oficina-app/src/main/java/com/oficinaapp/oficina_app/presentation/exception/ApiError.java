package com.oficinaapp.oficina_app.presentation.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.Map;

/**
 * The single error shape every endpoint answers with.
 *
 * @param fields field name to message, present only when the request failed
 *               validation.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiError(

        Instant timestamp,
        int status,
        String error,
        String message,
        Map<String, String> fields

) {

    public static ApiError of(HttpStatus status, String message) {
        return new ApiError(Instant.now(), status.value(), status.getReasonPhrase(), message, null);
    }

    public static ApiError of(HttpStatus status, String message, Map<String, String> fields) {
        return new ApiError(Instant.now(), status.value(), status.getReasonPhrase(), message, fields);
    }
}
