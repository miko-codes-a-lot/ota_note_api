package com.ota.api.note.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * PaginatedDTO represents a generic data transfer object for paginated responses in RESTful APIs.
 * It encapsulates a list of items, pagination metadata such as page index, total pages, and total items.
 *
 * @param <T> The type of items contained in the paginated response.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedDTO<T> {
    /**
     * A list of items included in the paginated response.
     */
    private List<T> items;

    /**
     * The index of the current page within the paginated response.
     */
    private int pageIndex;

    /**
     * The total number of pages available for the paginated response.
     */
    private int totalPages;

    /**
     * The total number of items available in the entire dataset.
     */
    private long totalItems;
}
