package com.ota.api.note.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Optional;

/**
 * Controller advice class that provides guidance on handling specific exceptions globally.
 * This class is responsible for handling exceptions and providing friendly error messages
 * to the clients instead of returning stack traces.
 *
 * @author Miko Chu
 * @since 2024-04-27
 */
@ControllerAdvice
public class AdviseController {

    /**
     * Handles {@link MethodArgumentTypeMismatchException} by providing a friendly error message
     * based on the type of argument mismatch.
     *
     * @param ex The {@link MethodArgumentTypeMismatchException} to be handled.
     * @return A {@link ResponseEntity} containing a friendly error message and HTTP status code.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleArgsTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String name = Optional.ofNullable(ex.getRequiredType())
                .map(Class::getName)
                .orElse("");

        String message = switch (name) {
            case "string" -> STR."'\{ ex.getPropertyName() }' must be a text";
            case "long" -> STR."'\{ ex.getPropertyName() }' must be a number";
            default -> ex.getMessage();
        };

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
