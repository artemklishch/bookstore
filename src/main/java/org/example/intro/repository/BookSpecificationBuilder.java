package org.example.intro.repository;

import lombok.RequiredArgsConstructor;
import org.example.intro.dto.BookSearchParametersDto;
import org.example.intro.model.Book;
import org.example.intro.repository.book.SpecificationBuilder;
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
        System.out.println("searchParameters: " + searchParameters);
        Specification<Book> spec = Specification.where(null);
        if (searchParameters.titles() != null && searchParameters.titles().length > 0) {
            spec = spec.and(bookBookSpecificationProviderManager
                    .getSpecificationProvider(TITLE)
                    .getSpecification(searchParameters.titles()));
        }
        if (searchParameters.authors() != null && searchParameters.authors().length > 0) {
            spec = spec.and(bookBookSpecificationProviderManager
                    .getSpecificationProvider(AUTHOR)
                    .getSpecification(searchParameters.authors()));
        }
        if (searchParameters.isbns() != null && searchParameters.isbns().length > 0) {
            spec = spec.and(bookBookSpecificationProviderManager
                    .getSpecificationProvider(ISBN)
                    .getSpecification(searchParameters.isbns()));
        }
        return spec;
    }
}
