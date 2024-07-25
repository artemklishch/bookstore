package org.example.intro.service;

import java.util.List;
import org.example.intro.dto.BookDto;
import org.example.intro.dto.BookSearchParametersDto;
import org.example.intro.dto.CreateBookDto;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookDto requestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    void delete(Long id);

    List<BookDto> search(BookSearchParametersDto params, Pageable pageable);
}
