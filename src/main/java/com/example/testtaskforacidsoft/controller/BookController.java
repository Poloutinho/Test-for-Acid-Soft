package com.example.testtaskforacidsoft.controller;

import com.example.testtaskforacidsoft.dto.BookDto;
import com.example.testtaskforacidsoft.dto.BookSearchParameters;
import com.example.testtaskforacidsoft.model.Book;
import com.example.testtaskforacidsoft.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public BookDto createBook(@RequestBody Book book) {
        return bookService.save(book);
    }

    @GetMapping
    public List<BookDto> getAllBooks(Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PutMapping("/{id}")
    public BookDto updateBook(@PathVariable Long id, @RequestBody BookDto bookDetails) {
        BookDto updatedBook = bookService.updateById(id, bookDetails);
        return updatedBook;
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteById(id);
    }

    @GetMapping("/search")
    public List<BookDto> searchBooks(BookSearchParameters searchParameters) {
        return bookService.search(searchParameters);
    }
}
