package org.example.intro.mapper;

import org.example.intro.config.MapperConfig;
import org.example.intro.dto.BookDto;
import org.example.intro.dto.CreateBookDto;
import org.example.intro.model.Book;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toBookDto(Book book);

    Book toBook(CreateBookDto createDto);
}
