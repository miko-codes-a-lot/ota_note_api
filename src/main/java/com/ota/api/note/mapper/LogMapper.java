package com.ota.api.note.mapper;

import com.ota.api.note.models.dto.LogDTO;
import com.ota.api.note.models.entity.Log;
import org.springframework.stereotype.Service;

import static com.ota.api.note.utils.DateUtils.toISODate;
import static com.ota.api.note.utils.DateUtils.toISOString;

@Service
public class LogMapper implements Mapper<Log, LogDTO> {
    @Override
    public LogDTO toDTO(Log entity) {
        return LogDTO.builder()
                .id(entity.getId())
                .tracingId(entity.getTracingId())
                .ipAddress(entity.getIpAddress())
                .method(entity.getMethod())
                .path(entity.getPath())
                .requestBody(entity.getRequestBody())
                .status(entity.getStatus())
                .timestamp(toISOString(entity.getTimestamp()))
                .responseBody(entity.getResponseBody())
                .build();
    }

    @Override
    public Log toEntity(LogDTO dto) {
        return Log.builder()
                .id(dto.getId())
                .tracingId(dto.getTracingId())
                .ipAddress(dto.getIpAddress())
                .method(dto.getMethod())
                .path(dto.getPath())
                .requestBody(dto.getRequestBody())
                .status(dto.getStatus())
                .responseBody(dto.getResponseBody())
                .timestamp(toISODate(dto.getTimestamp()))
                .build();
    }
}
