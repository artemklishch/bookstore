package org.example.intro.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.example.intro.dto.book.BookDto;
import org.example.intro.dto.book.CreateBookDto;
import org.example.intro.mapper.BookMapper;
import org.example.intro.model.Book;
import org.example.intro.repository.book.BookRepository;
import org.example.intro.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book testBook;

    private BookDto testBookDto;

    @BeforeEach
    void beforeEach() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Title");
        book.setAuthor("Author");
        book.setDescription("Description");
        book.setPrice(BigDecimal.valueOf(10));
        book.setIsbn("isbn");
        this.testBook = book;

        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setDescription(book.getDescription());
        bookDto.setPrice(book.getPrice());
        this.testBookDto = bookDto;
    }

    @Test
    @DisplayName("Verify fetching normal BookDto list")
    void findAll_ReturnsBookDtos(){
        Page<Book> bookPage = new PageImpl<>(Collections.singletonList(this.testBook));
        when(bookRepository.findAll(PageRequest.of(0,10)))
                .thenReturn(bookPage);
        when(bookMapper.toDto(this.testBook)).thenReturn(this.testBookDto);

        List<BookDto> bookDtos = bookService.findAll(
                PageRequest.of(0,10)
        );

        assertEquals(1, bookDtos.size());
        assertEquals(this.testBookDto, bookDtos.get(0));
    }

    @Test
    @DisplayName("Verify save() method works")
    void saveBook_ReturnsBookDto(){
        CreateBookDto requestDto = new CreateBookDto()
                .setAuthor(this.testBook.getAuthor())
                .setTitle(this.testBook.getTitle())
                .setDescription(this.testBook.getDescription())
                .setCategories(List.of(1L))
                .setPrice(this.testBook.getPrice())
                .setIsbn(this.testBook.getIsbn());
        when(bookMapper.toEntity(requestDto)).thenReturn(this.testBook);
        when(bookRepository.save(this.testBook)).thenReturn(this.testBook);
        when(bookMapper.toDto(this.testBook)).thenReturn(this.testBookDto);

        BookDto actual = bookService.save(requestDto);

        assertEquals(requestDto.getTitle(), actual.getTitle());
        assertEquals(this.testBookDto, actual);
    }

    @Test
    @DisplayName("Verify find book by ID")
    void findBookById_WithExistingId_ReturnsBookDto(){
        Long id = this.testBook.getId();
        when(bookRepository.findById(id)).thenReturn(Optional.of(this.testBook));
        when(bookMapper.toDto(this.testBook)).thenReturn(this.testBookDto);

        BookDto actual = bookService.findById(id);

        assertEquals(this.testBookDto, actual);
        assertEquals(this.testBookDto.getId(), actual.getId());
    }

    @Test
    @DisplayName("Verify throw error if book not found by ID")
    void findBookById_WithNonExistingId_ThrowsException(){
        Long wrongId = 100L;
        when(bookRepository.findById(wrongId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(
            NoSuchElementException.class, () -> bookService.findById(wrongId)
        );

        assertEquals(exception.getMessage(), "Book with id " + wrongId + " not found");
    }

    @Test
    @DisplayName("Verify update() method work")
    void updateBook_WithExistingId_ReturnsBookDto(){
        String updatedTitle = "UpdatedTitle";
        CreateBookDto requestDto = new CreateBookDto()
                .setAuthor(this.testBook.getAuthor())
                .setTitle(updatedTitle)
                .setDescription(this.testBook.getDescription())
                .setCategories(List.of(1L))
                .setPrice(this.testBook.getPrice())
                .setIsbn(this.testBook.getIsbn());
        when(bookRepository.findById(this.testBook.getId())).thenReturn(Optional.of(this.testBook));
        this.testBook.setTitle(requestDto.getTitle());
        when(bookRepository.save(this.testBook)).thenReturn(this.testBook);
        BookDto bookDto = new BookDto()
                .setAuthor(this.testBook.getAuthor())
                .setTitle(this.testBook.getTitle())
                .setDescription(this.testBook.getDescription())
                .setPrice(this.testBook.getPrice());
        when(bookMapper.toDto(this.testBook)).thenReturn(bookDto);

        BookDto actual = bookService.update(this.testBook.getId(), requestDto);

        assertEquals(requestDto.getTitle(), actual.getTitle());
        assertNotEquals(requestDto.getTitle(), this.testBookDto.getTitle());
    }

    @Test
    @DisplayName("Verify throwing error on update() method when book not found")
    void updateBook_WithNoExistingId_ThrowsException(){
        Long wrongId = 100L;
        String updatedTitle = "UpdatedTitle";
        CreateBookDto requestDto = new CreateBookDto()
                .setAuthor(this.testBook.getAuthor())
                .setTitle(updatedTitle)
                .setDescription(this.testBook.getDescription())
                .setCategories(List.of(1L))
                .setPrice(this.testBook.getPrice())
                .setIsbn(this.testBook.getIsbn());
        when(bookRepository.findById(wrongId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(
                NoSuchElementException.class,
                () -> bookService.update(wrongId, requestDto)
        );

        assertEquals(exception.getMessage(), "Book with id " + wrongId + " not found");
    }
}