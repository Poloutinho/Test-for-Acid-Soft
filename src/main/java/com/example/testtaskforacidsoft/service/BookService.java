package com.example.testtaskforacidsoft.service;

import com.example.testtaskforacidsoft.dto.BookDto;
import com.example.testtaskforacidsoft.dto.BookSearchParameters;
import com.example.testtaskforacidsoft.model.Book;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface BookService {
    BookDto save(Book requestDto);

    BookDto findById(Long id);

    BookDto updateById(Long id, BookDto book);

    void deleteById(Long id);

    List<BookDto> search(BookSearchParameters searchParameters);

    List<BookDto> findAll(Pageable pageable);
}
