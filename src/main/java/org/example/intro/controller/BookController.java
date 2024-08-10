package org.example.intro.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.intro.dto.book.BookDto;
import org.example.intro.dto.book.BookSearchParametersDto;
import org.example.intro.dto.book.CreateBookDto;
import org.example.intro.service.BookService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Book management", description = "Endpoints for book management")
@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    @Operation(summary = "Get all books", description = "Get all books, including query parameters")
    public List<BookDto> findAll(Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a book", description = "Create a book")
    public BookDto create(@RequestBody @Valid CreateBookDto requestDto) {
        return bookService.save(requestDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    @Operation(summary = "Get the book by ID", description = "Get the book by ID")
    public BookDto findById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete the book by ID", description = "Delete the book by ID")
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    @Operation(summary = "Update the book by ID", description = "Update the book by ID")
    public BookDto update(@PathVariable Long id, @RequestBody @Valid CreateBookDto requestDto) {
        return bookService.update(id, requestDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/search")
    @Operation(summary = "Get books by fields", description = "Get books by titles, authors, isbns")
    public List<BookDto> search(
            BookSearchParametersDto searchParameters,
            Pageable pageable
    ) {
        return bookService.search(searchParameters, pageable);
    }
}
