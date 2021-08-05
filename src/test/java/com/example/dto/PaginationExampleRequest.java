package com.example.dto;

import java.util.Objects;

public class PaginationExampleRequest {
    private final int page;
    private final int pageSize;
    private final String order;
    private final String sort;

    public PaginationExampleRequest(int page, int pageSize, String order, String sort) {
        this.page = page;
        this.pageSize = pageSize;
        this.order = order;
        this.sort = sort;
    }

    public int page() {
        return page;
    }

    public int pageSize() {
        return pageSize;
    }

    public String order() {
        return order;
    }

    public String sort() {
        return sort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PaginationExampleRequest that = (PaginationExampleRequest) o;
        return page == that.page && pageSize == that.pageSize && Objects.equals(order,
            that.order) && Objects.equals(sort, that.sort);
    }

    @Override
    public int hashCode() {
        return Objects.hash(page, pageSize, order, sort);
    }

    @Override
    public String toString() {
        return "PaginationExampleRequest{" +
            "page=" + page +
            ", pageSize=" + pageSize +
            ", order='" + order + '\'' +
            ", sort='" + sort + '\'' +
            '}';
    }
}
