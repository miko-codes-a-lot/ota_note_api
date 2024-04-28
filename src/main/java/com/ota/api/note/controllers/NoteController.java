package com.ota.api.note.controllers;

import com.ota.api.note.Config;
import com.ota.api.note.exceptions.NotFound;
import com.ota.api.note.models.dto.ApiErrorDTO;
import com.ota.api.note.models.dto.NoteDTO;
import com.ota.api.note.models.dto.PaginatedDTO;
import com.ota.api.note.models.form.NoteForm;
import com.ota.api.note.spring.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.ota.api.note.utils.JSONUtils.stringify;

/**
 * Controller for managing notes.
 * <p>
 * This controller provides endpoints for CRUD operations on notes.
 * It handles HTTP requests related to notes, including creating, retrieving,
 * updating, and deleting notes. Each operation is documented with OpenAPI annotations
 * to provide a clear contract for clients interacting with this API.
 * <p>
 * @author Miko Chu
 * @since 2024-04-28
 */
@Slf4j
@RestController
@RequestMapping("/api/notes")
public class NoteController {
    private static final String NOTE_NOT_FOUND = "Note not found.";

    private final Config config;

    @Autowired
    public NoteController(Config config) {
        this.config = config;
    }


    @Operation(summary = "Get a Note by its id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found the note"),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Note not found", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDTO.class)) })
    })
    @GetMapping("/{id}")
    public ResponseEntity<NoteDTO> findById(@PathVariable long id) {
        if (id == 0) {
            throw new NotFound(NOTE_NOT_FOUND);
        }

        log.info(STR."username: \{config.tracingIdKey()}");

        return new ResponseEntity<>(
                new NoteDTO(10L, "", ""),
                HttpStatus.OK
        );
    }

    @Operation(summary = "Retrieve the notes in paginated format")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found the notes"),
            @ApiResponse(responseCode = "400", description = "Request param violation"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDTO.class)) })
    })
    @GetMapping("/")
    public ResponseEntity<PaginatedDTO<NoteDTO>> findAll(
            @RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex,
            @RequestParam(value = "sizeSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "search", defaultValue = "") String search
    ) {
        List<NoteDTO> notes = new ArrayList<>();
        notes.add(new NoteDTO(10L, "a1", "b1"));
        notes.add(new NoteDTO(11L, "a2", "b2"));
        notes.add(new NoteDTO(12L, "a3", "b3"));
        notes.add(new NoteDTO(13L, "a4", "b4"));
        notes.add(new NoteDTO(14L, "a5", "b5"));

        val paginate = PaginatedDTO.<NoteDTO>builder()
                .data(notes)
                .currentPage(1)
                .totalPages(5)
                .totalItems(50)
                .build();

        return Response.<PaginatedDTO<NoteDTO>>builder()
                .status(HttpStatus.OK)
                .body(paginate)
                .build();
    }

    @Operation(summary = "Create a new note")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Successfully created the note"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDTO.class)) }),
            @ApiResponse(responseCode = "409", description = "Note already exists", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDTO.class)) })
    })
    @PostMapping("/")
    public ResponseEntity<NoteDTO> create(@Valid @RequestBody NoteForm noteForm) {
        val note = NoteDTO.builder()
                .id(noteForm.getId())
                .title(noteForm.getTitle())
                .body(noteForm.getBody())
                .build();

        log.info("micooo");
        log.info(stringify(note));

        return Response.<NoteDTO>builder()
                .body(note)
                .status(HttpStatus.CREATED)
                .build();
    }

    @Operation(summary = "Update a new note")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully updated the note"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Note not found", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDTO.class)) }),
            @ApiResponse(responseCode = "409", description = "Conflict, note exists", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDTO.class)) })
    })
    @PutMapping("/")
    public ResponseEntity<NoteDTO> update(@Valid @RequestBody NoteForm noteForm) {
        val note = NoteDTO.builder()
                .id(noteForm.getId())
                .title(noteForm.getTitle())
                .body(noteForm.getBody())
                .build();

        return Response.<NoteDTO>builder()
                .body(note)
                .status(HttpStatus.OK)
                .build();
    }

    @Operation(summary = "Delete a note by its id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Deleted the note", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = NoteDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Note not found", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDTO.class)) })
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) throws Exception {
        if (id == 0) {
            throw new NotFound(NOTE_NOT_FOUND);
        }

        return Response.builder()
                .status(HttpStatus.NO_CONTENT)
                .buildV();
    }
}
