package org.example.intro.repository.book;

import lombok.RequiredArgsConstructor;
import org.example.intro.dto.book.BookSearchParametersDto;
import org.example.intro.model.Book;
import org.example.intro.repository.SpecificationProviderManager;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private final SpecificationProviderManager<Book> bookBookSpecificationProviderManager;
    public static final String TITLE = "title";
    public static final String AUTHOR = "author";
    public static final String ISBN = "isbn";

    @Override
    public Specification<Book> build(BookSearchParametersDto searchParameters) {
        Specification<Book> spec = Specification.where(null);
        if (searchParameters.getTitles() != null && searchParameters.getTitles().length > 0) {
            spec = spec.and(bookBookSpecificationProviderManager
                    .getSpecificationProvider(TITLE)
                    .getSpecification(searchParameters.getTitles()));
        }
        if (searchParameters.getAuthors() != null && searchParameters.getAuthors().length > 0) {
            spec = spec.and(bookBookSpecificationProviderManager
                    .getSpecificationProvider(AUTHOR)
                    .getSpecification(searchParameters.getAuthors()));
        }
        if (searchParameters.getIsbns() != null && searchParameters.getIsbns().length > 0) {
            spec = spec.and(bookBookSpecificationProviderManager
                    .getSpecificationProvider(ISBN)
                    .getSpecification(searchParameters.getIsbns()));
        }
        return spec;
    }
}
