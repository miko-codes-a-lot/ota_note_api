package com.ota.api.note.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PaginateParamsDTO represents a reusable data transfer object for representing request parameters
 * related to pagination in RESTful APIs.
 * It encapsulates query string, sorting criteria, page index, and page size.
 *
 * @author Miko Chu
 * @since 2024-04-28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginateParamsDTO {
    /**
     * The query string used for filtering items in the paginated response.
     */
    private String query = "";

    /**
     * The sorting criteria used for ordering items in the paginated response.
     */
    private String sortBy = "";

    /**
     * The index of the page to retrieve in the paginated response. Defaults to 0.
     */
    private int page = 0;
    private int pageSize = 10;

    /**
     * The number of items per page in the paginated response. Defaults to 10.
     * If set to a non-positive value, it defaults to 10.
     *
     * @return The effective page size.
     */
    public int getPageSize() {
        return pageSize <= 0 ? 10 : pageSize;
    }

    /**
     * Get the sorting criteria with a default value if not specified.
     *
     * @param defValue The default value to use if sortBy is empty.
     * @return The sorting criteria.
     */
    public String getSortBy(String defValue) {
        return sortBy.isEmpty() ? defValue : sortBy;
    }
}
