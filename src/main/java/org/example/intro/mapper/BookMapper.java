package org.example.intro.mapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.example.intro.config.MapperConfig;
import org.example.intro.dto.book.BookDto;
import org.example.intro.dto.book.BookDtoWithoutCategoryIds;
import org.example.intro.dto.book.CreateBookDto;
import org.example.intro.model.Book;
import org.example.intro.model.Category;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    @Mapping(target = "categoryIds", ignore = true)
    BookDto toDto(Book book);
    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book){
        List<Long> categoryIds = book.getCategories().stream()
                .map(Category::getId)
                .toList();
        bookDto.setCategoryIds(categoryIds);
    }

    @Mapping(target = "categories", ignore = true)
    Book toEntity(CreateBookDto createDto);
    @AfterMapping
    default void setCategories(@MappingTarget Book book, CreateBookDto requestDto) {
        Set<Long> categoryIds = new HashSet<>(requestDto.getCategories());
        Set<Category> categories = categoryIds.stream()
                .map(Category::new)
                .collect(Collectors.toSet());
        book.setCategories(categories);
    }

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);
}
