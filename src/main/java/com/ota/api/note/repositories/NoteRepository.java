package com.ota.api.note.repositories;

import com.ota.api.note.models.entity.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    @Query("""
        SELECT n
        FROM Note n
        WHERE n.title ILIKE %?1 OR
            n.body ILIKE %?1
        """)
    Page<Note> findAllByQuery(String query, Pageable pageable);
}