package com.ota.api.note.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ota.api.note.models.dto.NoteDTO;
import com.ota.api.note.models.dto.PaginateParamsDTO;
import com.ota.api.note.models.dto.PaginatedDTO;
import com.ota.api.note.models.form.NoteForm;
import com.ota.api.note.services.LogService;
import com.ota.api.note.services.NoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;

import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

import static com.ota.api.note.utils.DateUtils.toISOString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NoteController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
public class NoteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    @MockBean
    private LogService logService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void NoteController_GetNotes_ReturnNotes() throws Exception {
        List<NoteDTO> items = IntStream.range(1, 10)
                .mapToObj(i -> NoteDTO.builder()
                        .id(i)
                        .title(STR."title-\{i}")
                        .body(STR."body-\{i}")
                        .dateCreated(toISOString(new Date()))
                        .dateUpdated(toISOString(new Date()))
                        .build())
                .toList();
        PaginateParamsDTO params = new PaginateParamsDTO();

        when(noteService.findAll(params)).thenReturn(
                PaginatedDTO.<NoteDTO>builder()
                        .pageIndex(0)
                        .items(items)
                        .totalItems(100)
                        .totalPages(10)
                        .build()
        );

        mockMvc.perform(get("/api/notes/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.pageIndex", is(0)))
                .andExpect(jsonPath("$.totalItems", is(100)))
                .andExpect(jsonPath("$.totalPages", is(10)))
                .andExpect(jsonPath("$.items").isArray());
    }

    @Test
    public void NoteController_GetNoteById_ReturnNote() throws Exception {
        NoteDTO note = NoteDTO.builder()
                .id(1L)
                .title("Sample Title")
                .body("Sample Body")
                .dateCreated(toISOString(new Date()))
                .dateUpdated(toISOString(new Date()))
                .build();

        when(noteService.findOne(anyLong())).thenReturn(note);

        mockMvc.perform(get("/api/notes/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Sample Title")))
                .andExpect(jsonPath("$.body", is("Sample Body")));
    }
}
