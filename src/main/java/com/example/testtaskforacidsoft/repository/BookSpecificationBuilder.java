package com.example.testtaskforacidsoft.repository;

import com.example.testtaskforacidsoft.dto.BookSearchParameters;
import com.example.testtaskforacidsoft.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private final SpecificationProviderManager<Book> bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParameters searchParameters) {
        Specification<Book> spec = Specification.where(null);
        if (searchParameters.title() != null && searchParameters.title().length > 0) {
            spec = spec.and(bookSpecificationProviderManager.getSpecificationProvider("title")
                    .getSpecification(searchParameters.title()));
        }
        if (searchParameters.author() != null && searchParameters.author().length > 0) {
            spec = spec.and(bookSpecificationProviderManager.getSpecificationProvider("author")
                    .getSpecification(searchParameters.author()));
        }

        if (searchParameters.genre() != null && searchParameters.genre().length > 0) {
            spec = spec.and(bookSpecificationProviderManager.getSpecificationProvider("genre")
                    .getSpecification(searchParameters.genre()));
        }

        return spec;
    }
}
