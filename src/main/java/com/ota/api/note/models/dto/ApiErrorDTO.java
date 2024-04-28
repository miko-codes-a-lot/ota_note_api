package com.ota.api.note.models.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

/**
 * ApiErrorDTO represents the unified error response format for RESTful APIs.
 * It encapsulates the HTTP status, a message describing the error, and a list of detailed error messages.
 *
 * @author Miko Chu
 * @since 2024-04-29
 */
@Getter
public class ApiErrorDTO {
    /**
     * HTTP status code indicating the error.
     */
    private final HttpStatus status;

    /**
     * A message describing the error in a human-readable format.
     */
    private final String message;

    /**
     * A list of detailed error messages providing more context about the error.
     */
    private final List<String> errors;

    /**
     * Constructs an ApiErrorDTO object with the specified HTTP status, message, and list of errors.
     *
     * @param status  The HTTP status code indicating the error.
     * @param message A message describing the error in a human-readable format.
     * @param errors  A list of detailed error messages providing more context about the error.
     */
    public ApiErrorDTO(HttpStatus status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    /**
     * Constructs an ApiErrorDTO object with the specified HTTP status, message, and single error message.
     * This constructor is useful when there's only one error message to be included.
     *
     * @param status The HTTP status code indicating the error.
     * @param message A message describing the error in a human-readable format.
     * @param error A detailed error message providing more context about the error.
     */
    public ApiErrorDTO(HttpStatus status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        this.errors = Collections.singletonList(error);
    }
}
