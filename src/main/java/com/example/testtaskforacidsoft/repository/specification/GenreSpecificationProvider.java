package com.example.testtaskforacidsoft.repository.specification;

import com.example.testtaskforacidsoft.model.Book;
import com.example.testtaskforacidsoft.repository.SpecificationProvider;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class GenreSpecificationProvider implements SpecificationProvider<Book> {
    @Override
    public String getKey() {
        return "genre";
    }

    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) ->
                root.get("genre").in(Arrays.stream(params).toArray());
    }
}
