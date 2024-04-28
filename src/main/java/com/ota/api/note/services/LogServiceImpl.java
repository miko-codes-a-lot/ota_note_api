package com.ota.api.note.services;

import com.ota.api.note.mapper.LogMapper;
import com.ota.api.note.models.dto.LogDTO;
import com.ota.api.note.models.entity.Log;
import com.ota.api.note.repositories.LogRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService{
    private final LogRepository logRepository;
    private final LogMapper logMapper;

    @Autowired
    public LogServiceImpl(LogRepository logRepository, LogMapper logMapper) {
        this.logRepository = logRepository;
        this.logMapper = logMapper;
    }

    @Override
    public void write(LogDTO data) {
        val log = this.logRepository.save(Log.builder()
                .tracingId(data.getTracingId())
                .ipAddress(data.getIpAddress())
                .method(data.getMethod())
                .path(data.getPath())
                .requestBody(data.getRequestBody())
                .status(data.getStatus())
                .responseBody(data.getResponseBody())
                .build()
        );

        this.logMapper.toDTO(log);
    }
}
