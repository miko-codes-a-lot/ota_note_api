package com.ota.api.note.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogDTO {
    private Long id;
    private String tracingId;
    private String ipAddress;
    private String method;
    private String path;
    private String timestamp;
    private int status;
    private String requestBody;
    private String responseBody;
}
