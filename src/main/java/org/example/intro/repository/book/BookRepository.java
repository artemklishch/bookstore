package org.example.intro.repository.book;

import java.util.List;
import java.util.Optional;
import org.example.intro.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends
        JpaRepository<Book, Long>,
        JpaSpecificationExecutor<Book>{
    @EntityGraph(attributePaths = "categories")
    Page<Book> findAll(Pageable pageable);

    @EntityGraph(attributePaths = "categories")
    Page<Book> findAll(Specification specification, Pageable pageable);

    @EntityGraph(attributePaths = "categories")
    Optional<Book> findById(Long id);

    @Query(
            "SELECT DISTINCT b FROM Book b"
                    + " LEFT JOIN FETCH b.categories c WHERE c.id = :id"
    )
    List<Book> findBooksByCategoryId(Long id, Pageable pageable);
}
