package com.ota.api.note.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class SimpleError extends RuntimeException {
    public SimpleError(String message) {
        super(message);
    }
}
