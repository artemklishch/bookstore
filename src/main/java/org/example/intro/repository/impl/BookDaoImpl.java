package org.example.intro.repository.impl;

import java.util.List;
import org.example.intro.model.Book;
import org.example.intro.repository.BookRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class BookDaoImpl implements BookRepository {
    @Autowired
    private final SessionFactory sessionFactory;

    @Autowired
    public BookDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Book save(Book book) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(book);
            transaction.commit();
            return book;
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to save the book", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List findAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<Book> getBooksQuery = session.createQuery("from Book", Book.class);
            return getBooksQuery.getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to fetch books", e);
        }
    }
}
