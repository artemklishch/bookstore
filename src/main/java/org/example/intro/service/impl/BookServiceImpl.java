package org.example.intro.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.example.intro.dto.BookDto;
import org.example.intro.dto.BookSearchParametersDto;
import org.example.intro.dto.CreateBookDto;
import org.example.intro.mapper.BookMapper;
import org.example.intro.model.Book;
import org.example.intro.repository.BookSpecificationBuilder;
import org.example.intro.repository.book.BookRepository;
import org.example.intro.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;

    @Override
    public BookDto save(CreateBookDto requestDto) {
        Book book = bookMapper.toBook(requestDto);
        return bookMapper.toBookDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
                .map(bookMapper::toBookDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        return bookMapper.toBookDto(bookRepository.findById(id).orElse(null));
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDto> search(BookSearchParametersDto params, Pageable pageable) {
        String[] titles = params.titles();
        String[] authors = params.authors();
        String[] isbns = params.isbns();
        if (
                (titles == null || titles.length == 0) &&
                (authors == null || authors.length == 0) &&
                (isbns == null || isbns.length == 0)
        ) {
            return bookRepository.findAll(pageable).map(bookMapper::toBookDto).toList();
        } else {
            List<String> emptyList = Collections.emptyList();
            Page<Book> books = bookRepository.findBooksOnPage(
                    Objects.isNull(titles) ? emptyList : List.of(titles),
                    Objects.isNull(authors) ? emptyList : List.of(authors),
                    Objects.isNull(isbns) ? emptyList : List.of(isbns),
                    pageable
            );
            return books.getContent().stream().map(bookMapper::toBookDto).toList();
        }
    }
}
