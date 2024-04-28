package com.ota.api.note.models.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Log {
    @Id
    @GeneratedValue
    private Long id;
    private String tracingId;
    private String ipAddress;
    private String method;
    private String path;
    private int status;
    private String requestBody;
    private String responseBody;

    @CreationTimestamp
    private Date timestamp;
}
