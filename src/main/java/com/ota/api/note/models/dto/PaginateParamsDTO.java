package com.ota.api.note.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginateParamsDTO {
    private String query = "";
    private String sortBy = "";
    private int page = 0;
    private int pageSize = 10;

    public int getPageSize() {
        return pageSize <= 0 ? 10 : pageSize;
    }

    public String getSortBy(String defValue) {
        return sortBy.isEmpty() ? defValue : sortBy;
    }
}
