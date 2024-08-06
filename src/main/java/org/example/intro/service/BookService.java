package org.example.intro.service;

import java.util.List;
import org.example.intro.dto.book.BookDto;
import org.example.intro.dto.book.BookDtoWithoutCategoryIds;
import org.example.intro.dto.book.BookSearchParametersDto;
import org.example.intro.dto.book.CreateBookDto;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookDto requestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    void delete(Long id);

    List<BookDto> search(BookSearchParametersDto params, Pageable pageable);

    BookDto update(Long id, CreateBookDto requestDto);

    List<BookDtoWithoutCategoryIds> getBooksByCategoryId(Long categoryId, Pageable pageable);
}
