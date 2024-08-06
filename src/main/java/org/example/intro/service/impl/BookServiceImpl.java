package org.example.intro.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.example.intro.dto.book.BookDto;
import org.example.intro.dto.book.BookDtoWithoutCategoryIds;
import org.example.intro.dto.book.BookSearchParametersDto;
import org.example.intro.dto.book.CreateBookDto;
import org.example.intro.mapper.BookMapper;
import org.example.intro.model.Book;
import org.example.intro.model.Category;
import org.example.intro.repository.book.BookSpecificationBuilder;
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
        Book book = bookMapper.toEntity(requestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
                .map(bookMapper::toDto).toList();
    }

    @Override
    public BookDto findById(Long id) {
        return bookMapper.toDto(bookRepository.findById(id).orElse(null));
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDto> search(BookSearchParametersDto params, Pageable pageable) {
        Specification<Book> bookSpecification = bookSpecificationBuilder.build(params);
        return bookRepository.findAll(bookSpecification, pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto update(Long id, CreateBookDto requestDto) {
        Book bookFromDB = bookRepository.findById(id).orElse(null);
        if (bookFromDB == null) {
            throw new NoSuchElementException("Book with the id " + id + " does not exist");
        }
        bookFromDB.setTitle(requestDto.getTitle());
        bookFromDB.setAuthor(requestDto.getAuthor());
        bookFromDB.setPrice(requestDto.getPrice());
        bookFromDB.setDescription(requestDto.getDescription());
        bookFromDB.setIsbn(requestDto.getIsbn());
        bookFromDB.setCoverImage(requestDto.getCoverImage());
        Set<Category> categories = requestDto.getCategories().stream()
                .map(Category::new)
                .collect(Collectors.toSet());
        bookFromDB.setCategories(categories);
        bookRepository.save(bookFromDB);
        return bookMapper.toDto(bookFromDB);
    }

    @Override
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(Long id, Pageable pageable) {
        List<Book> books = bookRepository.findBooksByCategoryId(id, pageable);
        return books.stream()
                .map(bookMapper::toDtoWithoutCategories)
                .toList();
    }
}
