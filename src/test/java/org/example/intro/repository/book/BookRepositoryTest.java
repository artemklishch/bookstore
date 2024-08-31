package org.example.intro.repository.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.List;
import org.example.intro.dto.book.BookSearchParametersDto;
import org.example.intro.model.Book;
import org.example.intro.util.HandleDefaultDBData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest extends HandleDefaultDBData {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookSpecificationBuilder bookSpecificationBuilder;

    @Test
    @DisplayName("Verify fetching all books")
    void findAll_ReturnsBooks() {
        Page<Book> bookPage = bookRepository.findAll(PageRequest.of(0, 2));
        List<Book> actual = bookPage.getContent();
        assertEquals(2, actual.size());
    }

    @Test
    @DisplayName("Verify fetching book by id")
    void findById_WithExistingId_ReturnsBook() {
        Long id = 1L;
        Book actual = bookRepository.findById(id).orElse(null);
        assertNotNull(actual);
        assertEquals(2, actual.getCategories().size());
    }

    @Test
    @DisplayName("Verify fetching by category ID")
    void findByCategoryId_WithCategoryId_ReturnsBooks() {
        Long categoryId = 1L;
        List<Book> actual = bookRepository.findBooksByCategoryId(
                categoryId, PageRequest.of(0, 10)
        );
        assertEquals(3, actual.size());
    }

    @Test
    @DisplayName("Verify fetching by authors query param")
    void findByQueryParam_WithAuthorsParam_ReturnsBooks() {
        String[] authors = {"boris"};
        BookSearchParametersDto params = new BookSearchParametersDto()
                .setAuthors(authors);
        Specification<Book> bookSpecification = bookSpecificationBuilder.build(params);
        Page<Book> actual = bookRepository.findAll(
                bookSpecification, PageRequest.of(0, 10)
        );
        assertEquals(1, actual.getContent().size());
    }
}