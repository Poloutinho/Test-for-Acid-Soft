package com.example.testtaskforacidsoft.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.testtaskforacidsoft.dto.BookDto;
import com.example.testtaskforacidsoft.mapper.BookMapper;
import com.example.testtaskforacidsoft.model.Book;
import com.example.testtaskforacidsoft.repository.BookRepository;
import com.example.testtaskforacidsoft.service.BookService;
import java.util.Collections;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    private BookDto bookDto;

    @BeforeEach
    public void setUp() {
        bookDto = new BookDto();
        bookDto.setTitle("Test Book");
        bookDto.setAuthor("Test Author");
        bookDto.setPublicationYear(2020);
        bookDto.setGenre("Novel");
        bookDto.setIsbn("1234567890123");

        Book book = bookMapper.toModel(bookDto);
        bookRepository.save(book);
    }

    @AfterEach
    public void tearDown() {
        bookRepository.deleteAll();
    }

    @Test
    void createBook_ValidRequest_ReturnsBook() throws Exception {
        BookDto bookDto = new BookDto();
        bookDto.setTitle("Test Book");
        bookDto.setAuthor("Test Author");
        bookDto.setPublicationYear(2020);
        bookDto.setGenre("Novel");
        bookDto.setIsbn("1234567890121");

        bookRepository.save(bookMapper.toModel(bookDto));

        Mockito.when(bookService.save(Mockito.any(BookDto.class))).thenReturn(bookDto);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"title\": \"Test Book\","
                                + " \"author\": \"Test Author\","
                                + " \"publicationYear\": 2020,"
                                + " \"genre\": \"Novel\","
                                + " \"isbn\": \"1234567890124\" }"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.title")
                        .value("Test Book"))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.author")
                        .value("Test Author"))
                .andDo(print());
    }

    @Test
    void getAllBooks_ValidRequest_ReturnsBooks() throws Exception {
        Mockito.when(bookService.findAll(Mockito.any()))
                .thenReturn(new PageImpl<>(Collections.singletonList(bookDto)));

        mockMvc.perform(get("/api/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.content[0].title")
                        .value("Test Book"))
                .andDo(print());
    }

    @Test
    void getBookById_ValidRequest_ReturnsBook() throws Exception {
        Mockito.when(bookService.findById(1L)).thenReturn(bookDto);

        mockMvc.perform(get("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Test Book"))
                .andDo(print());
    }

    @Test
    void updateBook_ValidRequest_ReturnsUpdatedBook() throws Exception {
        BookDto updatedBookDto = new BookDto();
        updatedBookDto.setTitle("Updated Book");
        updatedBookDto.setAuthor("Updated Author");
        updatedBookDto.setPublicationYear(2020);

        Mockito.when(bookService.updateById(Mockito.anyLong(), Mockito.any(BookDto.class)))
                .thenReturn(updatedBookDto);

        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"title\": \"Updated Book\","
                                + " \"author\": \"Updated Author\","
                                + " \"publicationYear\": 2020,"
                                + " \"genre\": \"Novel\","
                                + " \"isbn\": \"1234567890124\" }"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Updated Book"))
                .andDo(print());
    }

    @Test
    void deleteBook_ValidRequest_DeletesBook() throws Exception {
        Mockito.doNothing().when(bookService).deleteById(1L);

        mockMvc.perform(delete("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        Mockito.verify(bookService, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void createBook_InvalidIsbn_ReturnsNotFound() throws Exception {
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"title\": \"Test Book\","
                                + " \"author\": \"Test Author\","
                                + " \"publicationYear\": 2020,"
                                + " \"genre\": \"Novel\", "
                                + "\"isbn\": \"invalidisbn\" }"))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.isbn")
                        .value("ISBN must be exactly 13 characters long"))
                .andDo(print());
    }
}
