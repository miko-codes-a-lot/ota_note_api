package com.ota.api.note.models.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ErrorDTO {
    private LocalDate timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
