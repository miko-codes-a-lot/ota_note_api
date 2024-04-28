package com.ota.api.note.services;

import com.ota.api.note.models.dto.NoteDTO;
import com.ota.api.note.models.dto.PaginateParamsDTO;
import com.ota.api.note.models.dto.PaginatedDTO;

public interface NoteService {
    NoteDTO create(NoteDTO noteDTO);
    NoteDTO findOne(Long id);
    PaginatedDTO<NoteDTO> findAll(PaginateParamsDTO paginateParams);
}

