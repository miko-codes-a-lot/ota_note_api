package com.ota.api.note.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedDTO<T> {
    private List<T> items;
    private int pageIndex;
    private int totalPages;
    private long totalItems;
}
