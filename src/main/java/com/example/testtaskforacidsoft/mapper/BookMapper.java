package com.example.testtaskforacidsoft.mapper;

import com.example.testtaskforacidsoft.config.MapperConfig;
import com.example.testtaskforacidsoft.dto.BookDto;
import com.example.testtaskforacidsoft.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(BookDto dto);

    @Mapping(target = "id", ignore = true)
    Book updateBookFromDto(BookDto updatedBookDto, @MappingTarget Book book);
}
