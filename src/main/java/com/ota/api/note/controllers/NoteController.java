package com.ota.api.note.controllers;

import com.ota.api.note.models.dto.ApiErrorDTO;
import com.ota.api.note.models.dto.NoteDTO;
import com.ota.api.note.models.dto.PaginateParamsDTO;
import com.ota.api.note.models.dto.PaginatedDTO;
import com.ota.api.note.models.form.NoteForm;
import com.ota.api.note.services.NoteService;
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
    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @Operation(summary = "Get a Note by its id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found the note"),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Note not found", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDTO.class)) })
    })
    @GetMapping("/{id}")
    public ResponseEntity<NoteDTO> findById(@PathVariable Long id) {
        val note = this.noteService.findOne(id);

        return Response.<NoteDTO>builder()
                .status(HttpStatus.OK)
                .body(note)
                .build();
    }

    @Operation(summary = "Retrieve the notes in paginated format")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found the notes"),
            @ApiResponse(responseCode = "400", description = "Request param violation"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDTO.class)) })
    })
    @GetMapping("/")
    public ResponseEntity<PaginatedDTO<NoteDTO>> findAll(PaginateParamsDTO paginateParams) {
        val notePage = this.noteService.findAll(paginateParams);

        return Response.<PaginatedDTO<NoteDTO>>builder()
                .status(HttpStatus.OK)
                .body(notePage)
                .build();
    }

    @Operation(summary = "Create a new note, ignores the id")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Successfully created the note"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDTO.class)) }),
            @ApiResponse(responseCode = "409", description = "Note already exists", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDTO.class)) })
    })
    @PostMapping("/")
    public ResponseEntity<NoteDTO> create(@Valid @RequestBody NoteForm noteForm) {
        val note = NoteDTO.builder()
                .title(noteForm.getTitle())
                .body(noteForm.getBody())
                .build();

        return Response.<NoteDTO>builder()
                .body(this.noteService.create(note))
                .status(HttpStatus.CREATED)
                .build();
    }

    @Operation(summary = "Update a new note")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully updated the note"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Note not found", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorDTO.class)) })
    })
    @PutMapping("/{id}")
    public ResponseEntity<NoteDTO> update(@PathVariable Long id, @Valid @RequestBody NoteForm noteForm) {
        val note = NoteDTO.builder()
                .id(id)
                .title(noteForm.getTitle())
                .body(noteForm.getBody())
                .build();

        return Response.<NoteDTO>builder()
                .body(this.noteService.update(note))
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
    public ResponseEntity<Void> delete(@PathVariable Long id) throws Exception {
        this.noteService.deleteOne(id);

        return Response.builder()
                .status(HttpStatus.NO_CONTENT)
                .buildV();
    }
}
