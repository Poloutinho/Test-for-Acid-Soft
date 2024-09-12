package com.example.testtaskforacidsoft.service;

import com.example.testtaskforacidsoft.dto.BookDto;
import com.example.testtaskforacidsoft.dto.BookSearchParameters;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(BookDto requestDto);

    BookDto findById(Long id);

    BookDto updateById(Long id, BookDto book);

    void deleteById(Long id);

    List<BookDto> search(BookSearchParameters searchParameters);

    Page<BookDto> findAll(Pageable pageable);
}
