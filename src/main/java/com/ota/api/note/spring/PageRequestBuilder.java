package com.ota.api.note.spring;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

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
