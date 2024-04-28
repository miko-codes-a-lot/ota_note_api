package com.ota.api.note.controllers;

import com.ota.api.note.errors.NotFoundError;
import com.ota.api.note.errors.SimpleError;
import com.ota.api.note.models.dto.ApiErrorDTO;
import com.ota.api.note.spring.Response;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.NonNull;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controller advice class that provides guidance on handling specific exceptions globally.
 * This class is responsible for handling Spring exceptions and providing friendly error messages
 * to the clients instead of returning stack traces.
 * <p>
 * This class extends {@link ResponseEntityExceptionHandler}
 * to handle common Spring MVC exceptions and provide customized error responses.
 * <p>
 * {@author Miko Chu}
 * {@since 2024-04-27}
 */
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * Handles {@link MethodArgumentNotValidException} by extracting field and global errors
     * and building a user-friendly error response.
     *
     * @param ex      The {@link MethodArgumentNotValidException} thrown
     * @param headers The {@link HttpHeaders} of the response
     * @param status  The {@link org.springframework.http.HttpStatus} of the response
     * @param request The WebRequest
     * @return {@link org.springframework.http.ResponseEntity} with error details
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request
    ) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getDefaultMessage());
        }

        val apiError = new ApiErrorDTO(HttpStatus.BAD_REQUEST, "Invalid data", errors);

        return Response.builder()
                .body(apiError)
                .headers(new HttpHeaders())
                .status(apiError.getStatus())
                .build();
    }

    /**
     * Handles {@link MissingServletRequestParameterException} by building a user-friendly error response.
     *
     * @param ex      The {@link MissingServletRequestParameterException} thrown
     * @param headers The {@link HttpHeaders} of the response
     * @param status  The {@link HttpStatus} of the response
     * @param request The WebRequest
     * @return {@link ResponseEntity} with error details
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request
    ) {
        val error = STR."\{ex.getParameterName()} parameter is missing";
        val apiError = new ApiErrorDTO(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);

        return Response.builder()
                .body(apiError)
                .headers(new HttpHeaders())
                .status(apiError.getStatus())
                .build();
    }

    /**
     * Handles {@link ConstraintViolationException} by extracting constraint violation errors
     * and building a user-friendly error response.
     *
     * @param ex      The {@link ConstraintViolationException} thrown
     * @return {@link ResponseEntity} with error details
     */
    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            val err = STR."\{violation.getRootBeanClass().getName()} \{violation.getPropertyPath()}: \{violation.getMessage()}";
            errors.add(err);
        }

        val apiError =
                new ApiErrorDTO(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);

        return Response.builder()
                .body(apiError)
                .headers(new HttpHeaders())
                .status(apiError.getStatus())
                .build();
    }

    /**
     * Handles {@link MethodArgumentTypeMismatchException} by building a user-friendly error response.
     *
     * @param ex      The {@link MethodArgumentTypeMismatchException} thrown
     * @return {@link ResponseEntity} with error details
     */
    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex
    ) {
        val name = Optional.ofNullable(ex.getRequiredType())
                .map(type -> switch (type.getName()) {
                    case "java.lang.Long" -> "number";
                    case "java.lang.String" -> "text";
                    default -> type.getName();
                }).orElse("unknown");
        String error = STR."\{ex.getName()} should be of type \{name}";
        val apiError =
                new ApiErrorDTO(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);

        return Response.builder()
                .body(apiError)
                .headers(new HttpHeaders())
                .status(apiError.getStatus())
                .build();
    }

    /**
     * Handles {@link NotFoundError} by building a user-friendly error response.
     *
     * @param ex      The {@link NotFoundError} thrown
     * @return {@link ResponseEntity} with error details
     */
    @ExceptionHandler({ NotFoundError.class })
    public ResponseEntity<Object> handleResourceNotFound(NotFoundError ex) {
        val message = ex.getLocalizedMessage();
        val apiError = new ApiErrorDTO(HttpStatus.NOT_FOUND, message, message);

        return Response.builder()
                .body(apiError)
                .headers(new HttpHeaders())
                .status(apiError.getStatus())
                .build();
    }

    /**
     * Handles {@link SimpleError} by building a user-friendly error response.
     *
     * @param ex      The {@link NotFoundError} thrown
     * @return {@link ResponseEntity} with error details
     */
    @ExceptionHandler({ SimpleError.class })
    public ResponseEntity<Object> handleSimpleError(SimpleError ex) {
        val message = ex.getLocalizedMessage();
        val apiError = new ApiErrorDTO(HttpStatus.BAD_REQUEST, message, message);

        return Response.builder()
                .body(apiError)
                .headers(new HttpHeaders())
                .status(apiError.getStatus())
                .build();
    }

    /**
     * Handles unknown {@link Exception} and treat it as "Unknown Server Exception"
     * @param ex The {@link Exception} thrown
     * @return {@link ResponseEntity} with error details
     */
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleUnknownException(Exception ex) {
        val message = ex.getLocalizedMessage();
        val apiError = new ApiErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR, message, message);

        return Response.builder()
                .body(apiError)
                .headers(new HttpHeaders())
                .status(apiError.getStatus())
                .build();
    }
}
