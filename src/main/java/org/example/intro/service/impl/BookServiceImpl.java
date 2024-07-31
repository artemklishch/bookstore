package org.example.intro.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.intro.dto.book.BookDto;
import org.example.intro.dto.book.BookSearchParametersDto;
import org.example.intro.dto.book.CreateBookDto;
import org.example.intro.mapper.BookMapper;
import org.example.intro.model.Book;
import org.example.intro.repository.BookSpecificationBuilder;
import org.example.intro.repository.book.BookRepository;
import org.example.intro.service.BookService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
        Specification<Book> bookSpecification = bookSpecificationBuilder.build(params);
        return bookRepository.findAll(bookSpecification, pageable).stream()
                .map(bookMapper::toBookDto)
                .toList();
    }
}
