package com.ota.api.note.repositories;

import com.ota.api.note.models.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>The {@code LogRepository} extends {@link JpaRepository},
 * which provides methods for basic CRUD operations and querying capabilities.
 *
 * <p>Through this repository, you can perform operations such as saving new log entries,
 * retrieving log entries by their unique identifiers, updating existing log entries,
 * deleting log entries, and performing various other queries as needed.
 *
 * @author Miko Chu
 * @since 2024-04-28
 */
public interface LogRepository extends JpaRepository<Log, Long> {
}
