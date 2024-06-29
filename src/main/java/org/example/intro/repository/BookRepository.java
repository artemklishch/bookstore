package org.example.intro.repository;

import java.util.List;
import org.example.intro.model.Book;

public interface BookRepository {
    Book save(Book book);

    List findAll();
}
