package com.example.testtaskforacidsoft.dto;

import com.example.testtaskforacidsoft.validation.ValidPublicationYear;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BookDto {
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
}
