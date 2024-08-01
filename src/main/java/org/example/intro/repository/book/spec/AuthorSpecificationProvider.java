package org.example.intro.repository.book.spec;

import java.util.Arrays;
import org.example.intro.model.Book;
import org.example.intro.repository.BookSpecificationBuilder;
import org.example.intro.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class AuthorSpecificationProvider implements SpecificationProvider<Book> {
    @Override
    public String getKey() {
        return BookSpecificationBuilder.AUTHOR;
    }

    @Override
    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) -> Arrays.stream(params)
                .map(author -> criteriaBuilder.like(
                        criteriaBuilder.upper(root.get(BookSpecificationBuilder.AUTHOR)),
                        "%" + author.toUpperCase() + "%"
                ))
                .reduce(criteriaBuilder::or)
                .orElse(null);
    }
}
