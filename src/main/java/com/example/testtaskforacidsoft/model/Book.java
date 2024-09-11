package com.example.testtaskforacidsoft.model;

import com.example.testtaskforacidsoft.validation.ValidPublicationYear;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "books")
@NoArgsConstructor
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    @NotNull
    @ValidPublicationYear
    private Integer publicationYear;

    private String genre;

    @NotBlank(message = "ISBN is required")
    @Size(min = 13, max = 13, message = "ISBN must be exactly 13 characters long")
    @Column(unique = true)
    private String isbn;

    public Book(Long id) {
        this.id = id;
    }
}
