package com.ota.api.note.repositories;

import com.ota.api.note.models.entity.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * The NoteRepository interface provides access to the database for Note entities
 * and supports operations like saving, updating, and retrieving notes.
 * It extends JpaRepository to inherit basic CRUD operations provided by Spring Data JPA.
 *
 * @author Miko Chu
 * @since 2024-04-28
 */
@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    /**
     * Retrieves a page of Note entities based on the provided query string, allowing for pagination.
     *
     * @param query    The query string used for filtering notes. It searches for matches in both title and body fields.
     * @param pageable The pagination information, including page index, page size, and sorting criteria.
     * @return A page of Note entities matching the query string, sorted and paginated as specified.
     */
    @Query("""
        SELECT n
        FROM Note n
        WHERE n.title ILIKE %?1 OR
            n.body ILIKE %?1
        """)
    Page<Note> findAllByQuery(String query, Pageable pageable);
}