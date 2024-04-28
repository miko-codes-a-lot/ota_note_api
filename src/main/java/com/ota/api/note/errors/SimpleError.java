package com.ota.api.note.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom error class designed to abstract away the complexity of Spring Framework errors
 * and provide user-friendly error messages to clients.
 * This class extends RuntimeException and sets the HTTP status code to BAD_REQUEST.
 *
 * @author Miko Chu
 * @since 2024-04-28
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class SimpleError extends RuntimeException {
    public SimpleError(String message) {
        super(message);
    }
}
