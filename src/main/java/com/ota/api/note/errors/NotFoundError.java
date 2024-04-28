package com.ota.api.note.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom error class designed to abstract away the complexity of Spring Framework errors
 * and provide user-friendly error messages for resource not found scenarios.
 * This class extends RuntimeException and sets the HTTP status code to NOT_FOUND.
 *
 * @author Miko Chu
 * @since 2024-04-27
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundError extends RuntimeException {
    public NotFoundError(String s) {
        super(s);
    }
}
