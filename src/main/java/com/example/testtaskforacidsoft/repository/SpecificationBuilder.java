package com.example.testtaskforacidsoft.repository;

import com.example.testtaskforacidsoft.dto.BookSearchParameters;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {
    Specification<T> build(BookSearchParameters searchParameters);
}

