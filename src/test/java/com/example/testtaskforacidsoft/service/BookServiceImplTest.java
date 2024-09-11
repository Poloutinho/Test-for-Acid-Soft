package com.example.testtaskforacidsoft.service;

import com.example.testtaskforacidsoft.dto.BookDto;
import com.example.testtaskforacidsoft.exception.EntityNotFoundException;
import com.example.testtaskforacidsoft.mapper.BookMapper;
import com.example.testtaskforacidsoft.model.Book;
import com.example.testtaskforacidsoft.repository.BookRepository;
import com.example.testtaskforacidsoft.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {
    private static final Long ID = 1L;
    private Book book;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;
    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        book = new Book(ID);
        book.setTitle("White Fang");
        book.setAuthor("Jack London");
        book.setPublicationYear(1900);
        book.setGenre("Novel");
        book.setIsbn("1258794683187");
    }

    @Test
    @DisplayName("Save book with valid data")
    void saveBook_ValidData_Success() {
        BookDto requestDto = createBookDto();

        BookDto expected = createBookDto();

        Mockito.when(bookMapper.toModel(requestDto)).thenReturn(book);
        Mockito.when(bookMapper.toDto(book)).thenReturn(expected);
        Mockito.when(bookRepository.save(book)).thenReturn(book);

        BookDto actual = bookService.save(bookMapper.toModel(requestDto));

        Assertions.assertEquals(expected, actual);
        Mockito.verify(bookRepository, Mockito.times(1)).save(book);
    }

    @Test
    @DisplayName("Find all books in database")
    void findAllBooks_ValidPageable_Success() {
        List<Book> books = new ArrayList<>();
        books.add(book);

        List<BookDto> expected = new ArrayList<>();
        expected.add(createBookDto());

        Page<Book> bookPage = new PageImpl<>(books);

        Mockito.when(bookRepository.findAll(Mockito.any(Pageable.class))).thenReturn(bookPage);
        Mockito.when(bookMapper.toDto(book)).thenReturn(createBookDto());

        List<BookDto> actual = bookService.findAll(Mockito.mock(Pageable.class));
        Assertions.assertEquals(expected, actual);
        Mockito.verify(bookRepository, Mockito.times(1))
                .findAll(Mockito.any(Pageable.class));
    }

    @Test
    @DisplayName("Check for an exception if the book id is invalid")
    void findBookById_InvalidId_Failed() {
        Mockito.when(bookRepository.findById(ID)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> bookService.findById(ID));
        Mockito.verify(bookRepository, Mockito.times(1)).findById(ID);
    }

    @Test
    @DisplayName("Find book by valid id")
    void findBookById_ValidId_Success() {
        Mockito.when(bookRepository.findById(ID)).thenReturn(Optional.of(book));
        BookDto expected = createBookDto();
        Mockito.when(bookMapper.toDto(book)).thenReturn(expected);
        BookDto actual = bookService.findById(ID);
        Assertions.assertEquals(expected, actual);
        Mockito.verify(bookRepository, Mockito.times(1)).findById(ID);
    }

    @Test
    @DisplayName("Update book with valid id and data")
    void updateBook_ValidIdAndData_Success() {
        Book newBook = book;
        newBook.setTitle("Black Fang");
        newBook.setPublicationYear(1900);

        BookDto bookDto = createBookDto();
        bookDto.setTitle("Black Fang");
        bookDto.setPublicationYear(1900);

        BookDto expected = createBookDto();
        expected.setTitle(newBook.getTitle());
        expected.setPublicationYear(newBook.getPublicationYear());

        Mockito.when(bookRepository.findById(ID)).thenReturn(Optional.of(book));
        Mockito.when(bookMapper.toDto(newBook)).thenReturn(expected);
        Mockito.when(bookRepository.save(newBook)).thenReturn(newBook);

        BookDto actual = bookService.updateById(ID, bookDto);

        Assertions.assertEquals(expected.getTitle(), actual.getTitle());
        Assertions.assertEquals(expected.getPublicationYear(), actual.getPublicationYear());
        Assertions.assertEquals(expected, actual);
        Mockito.verify(bookRepository, Mockito.times(1)).findById(ID);
        Mockito.verify(bookRepository, Mockito.times(1)).save(newBook);
    }

    private BookDto createBookDto() {
        BookDto requestDto = new BookDto();
        requestDto.setTitle("White Fang");
        requestDto.setAuthor("Jack London");
        requestDto.setPublicationYear(2000);
        requestDto.setGenre("Novel");
        return requestDto;
    }
}

