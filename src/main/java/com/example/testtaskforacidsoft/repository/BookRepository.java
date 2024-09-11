package com.example.testtaskforacidsoft.repository;

import com.example.testtaskforacidsoft.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import java.util.Arrays;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    Page<Book> findAll(Pageable pageable);

    @Component
    class GenreSpecificationProvider implements SpecificationProvider<Book> {
        @Override
        public String getKey() {
            return "genre";
        }

        public Specification<Book> getSpecification(String[] params) {
            return (root, query, criteriaBuilder) ->
                    root.get("genre").in(Arrays.stream(params).toArray());
        }
    }
}
