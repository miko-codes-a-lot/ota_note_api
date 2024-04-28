package com.ota.api.note.services;

import com.ota.api.note.errors.SimpleError;
import com.ota.api.note.mapper.NoteMapper;
import com.ota.api.note.models.dto.NoteDTO;
import com.ota.api.note.models.dto.PaginateParamsDTO;
import com.ota.api.note.models.dto.PaginatedDTO;
import com.ota.api.note.models.entity.Note;
import com.ota.api.note.repositories.NoteRepository;
import com.ota.api.note.spring.PageRequestBuilder;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;
    private final String[] sortableFields = {
            "title", "dateCreated", "dateUpdated"
    };

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository, NoteMapper noteMapper) {
        this.noteRepository = noteRepository;
        this.noteMapper = noteMapper;
    }

    public NoteDTO create(NoteDTO noteDTO) {
        val note = Note.builder()
                .title(noteDTO.getTitle())
                .body(noteDTO.getBody())
                .build();
        return noteMapper.toDTO(noteRepository.save(note));
    }

    public PaginatedDTO<NoteDTO> findAll(PaginateParamsDTO paginateParams) {
        val sortBy = paginateParams.getSortBy("title");

        if (!Arrays.asList(sortableFields).contains(sortBy)) {
            throw new SimpleError(STR."Sorting '\{sortBy}' column is not supported.");
        }

        val pagination = new PageRequestBuilder()
                .pageNumber(paginateParams.getPage())
                .pageSize(paginateParams.getPageSize())
                .sort(Sort.by(sortBy).ascending())
                .build();

        val page = this.noteRepository.findAllByQuery(paginateParams.getQuery(), pagination);
        val notes = page.getContent().stream()
                .map(noteMapper::toDTO)
                .collect(Collectors.toList());

        return PaginatedDTO.<NoteDTO>builder()
                .totalPages(page.getTotalPages())
                .pageIndex(paginateParams.getPage())
                .totalItems(page.getTotalElements())
                .items(notes)
                .build();
    }
}
