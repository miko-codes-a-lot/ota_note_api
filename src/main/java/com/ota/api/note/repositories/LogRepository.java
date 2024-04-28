package com.ota.api.note.repositories;

import com.ota.api.note.models.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Long> {
}
