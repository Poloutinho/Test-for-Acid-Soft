package com.example.testtaskforacidsoft.service.impl;

import com.example.testtaskforacidsoft.dto.BookDto;
import com.example.testtaskforacidsoft.dto.BookSearchParameters;
import com.example.testtaskforacidsoft.exception.EntityNotFoundException;
import com.example.testtaskforacidsoft.mapper.BookMapper;
import com.example.testtaskforacidsoft.model.Book;
import com.example.testtaskforacidsoft.repository.BookRepository;
import com.example.testtaskforacidsoft.repository.BookSpecificationBuilder;
import com.example.testtaskforacidsoft.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;

    @Override
    public BookDto save(BookDto requestDto) {
        Book savedBook = bookRepository.save(bookMapper.toModel(requestDto));
        return bookMapper.toDto(savedBook);
    }

    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can`t find book by id: " + id)
        );
        return bookMapper.toDto(book);
    }

    @Override
    public BookDto updateById(Long id, BookDto updatedBookDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can`t find book by id: " + id));

        bookMapper.updateBookFromDto(updatedBookDto, book);

        bookRepository.save(book);

        return bookMapper.toDto(book);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDto> search(BookSearchParameters searchParameters) {
        Specification<Book> bookSpecification = bookSpecificationBuilder.build(searchParameters);
        return bookRepository.findAll(bookSpecification)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public Page<BookDto> findAll(Pageable pageable) {
        Page<Book> bookPage = bookRepository.findAll(pageable);

        List<BookDto> bookDtos = bookPage
                .stream()
                .map(bookMapper::toDto)
                .toList();

        return new PageImpl<>(bookDtos, pageable, bookPage.getTotalElements());
    }
}
