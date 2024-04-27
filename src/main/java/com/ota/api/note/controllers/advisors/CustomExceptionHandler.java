package com.ota.api.note.controllers.advisors;

import com.ota.api.note.exceptions.NotFound;
import com.ota.api.note.models.dto.ApiErrorDTO;
import com.ota.api.note.spring.Response;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * This class is responsible for handling custom exceptions and providing friendly error messages
 * to the clients instead of returning stack traces.
 * <p>
 * This class extends {@link ResponseEntityExceptionHandler}
 * to handle common Spring MVC exceptions and provide customized error responses.
 * <p>
 * {@author Miko Chu}
 * {@since 2024-04-28}
 */
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * Handles {@link NotFound} by building a user-friendly error response.
     *
     * @param ex      The {@link NotFound} thrown
     * @return {@link ResponseEntity} with error details
     */
    @ExceptionHandler({ NotFound.class })
    public ResponseEntity<Object> handleResourceNotFound(NotFound ex) {
        val message = ex.getLocalizedMessage();
        val apiError = new ApiErrorDTO(HttpStatus.NOT_FOUND, message, message);

        return Response.builder()
                .body(apiError)
                .headers(new HttpHeaders())
                .status(apiError.getStatus())
                .build();
    }
}
