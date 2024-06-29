package org.example.intro.service;

import java.util.List;
import org.example.intro.model.Book;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}
