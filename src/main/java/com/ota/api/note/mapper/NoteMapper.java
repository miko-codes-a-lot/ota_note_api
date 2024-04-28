package com.ota.api.note.mapper;

import com.ota.api.note.models.dto.NoteDTO;
import com.ota.api.note.models.entity.Note;
import org.springframework.stereotype.Service;

import static com.ota.api.note.utils.DateUtils.toISODate;
import static com.ota.api.note.utils.DateUtils.toISOString;

@Service
public class NoteMapper implements Mapper<Note, NoteDTO> {
    @Override
    public NoteDTO toDTO(Note note) {
        return NoteDTO.builder()
                .id(note.getId())
                .title(note.getTitle())
                .body(note.getBody())
                .dateCreated(toISOString(note.getDateCreated()))
                .dateUpdated(toISOString(note.getDateUpdated()))
                .build();
    }

    @Override
    public Note toEntity(NoteDTO dto) {
        return Note.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .body(dto.getBody())
                .dateCreated(toISODate(dto.getDateCreated()))
                .dateUpdated(toISODate(dto.getDateUpdated()))
                .build();
    }
}
