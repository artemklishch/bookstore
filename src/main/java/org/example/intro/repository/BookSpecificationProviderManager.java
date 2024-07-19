package org.example.intro.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.intro.model.Book;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {
    private final List<SpecificationProvider<Book>> bookSpecificationProviders;

    @Override
    public SpecificationProvider<Book> getSpecificationProvider(String key) {
        return bookSpecificationProviders.stream()
                .filter(p -> p.getKey().equals(key))
                .findFirst()
                .orElseThrow(
                        () -> new RuntimeException(
                                "Can't find correct specification provider for key: " + key
                        )
                );
    }
}
