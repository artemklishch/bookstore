package org.example.intro.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.intro.dto.BookDto;
import org.example.intro.dto.CreateBookDto;
import org.example.intro.mapper.BookMapper;
import org.example.intro.model.Book;
import org.example.intro.repository.BookRepository;
import org.example.intro.service.BookService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookDto requestDto) {
        Book book = bookMapper.toBook(requestDto);
        return bookMapper.toBookDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
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
}
