package org.example.intro.service;

import java.util.List;
import org.example.intro.dto.BookDto;
import org.example.intro.dto.BookSearchParametersDto;
import org.example.intro.dto.CreateBookDto;

public interface BookService {
    BookDto save(CreateBookDto requestDto);

    List<BookDto> findAll();

    BookDto findById(Long id);

    void delete(Long id);

    List<BookDto> search(BookSearchParametersDto params);
}
