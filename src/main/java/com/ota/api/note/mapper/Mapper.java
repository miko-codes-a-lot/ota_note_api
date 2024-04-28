package com.ota.api.note.mapper;

public interface Mapper<A, B> {
    B toDTO(A entity);
    A toEntity(B dto);
}
