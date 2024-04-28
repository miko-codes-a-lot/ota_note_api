package com.ota.api.note.services;

import com.ota.api.note.models.dto.LogDTO;

public interface LogService {
    void write(LogDTO log);
}
