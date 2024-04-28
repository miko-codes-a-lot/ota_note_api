package com.ota.api.note.spring;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * PageRequestBuilder is a builder class for constructing PageRequest objects, which represent
 * pagination requests in Spring applications.
 *
 * @author Miko Chu
 * @since 2024-04-28
 */
public class PageRequestBuilder {
    private int pageNumber;
    private int pageSize;
    private Sort sort;

    public PageRequestBuilder() {}

    public PageRequestBuilder pageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }

    public PageRequestBuilder pageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public PageRequestBuilder sort(Sort sort) {
        this.sort = sort;
        return this;
    }

    public PageRequest build() {
        return PageRequest.of(this.pageNumber, this.pageSize, this.sort);
    }
}
