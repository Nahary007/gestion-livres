package com.example.Service;

import com.example.entity.Book;
import com.example.entity.Author;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import java.util.List;

@Stateless
public class BookService {
    
    @PersistenceContext(unitName = "bookPU")
    private EntityManager em;
    
    public Book create(Book book) {
        if (book.getId() == null) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }
    
    public Book update(Book book) {
        return em.merge(book);
    }
    
    public void delete(Long id) {
        Book book = findById(id);
        if (book != null) {
            em.remove(book);
        }
    }
    
    public Book findById(Long id) {
        return em.find(Book.class, id);
    }
    
    public List<Book> findAll() {
        return em.createNamedQuery("Book.findAll", Book.class)
                .getResultList();
    }
    
    public List<Book> findAll(int page, int pageSize) {
        return em.createNamedQuery("Book.findAll", Book.class)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }
    
    public List<Book> findByTitle(String title) {
        return em.createNamedQuery("Book.findByTitle", Book.class)
                .setParameter("title", "%" + title.toLowerCase() + "%")
                .getResultList();
    }
    
    public List<Book> findByAuthor(Long authorId) {
        return em.createNamedQuery("Book.findByAuthor", Book.class)
                .setParameter("authorId", authorId)
                .getResultList();
    }
    
    public List<Book> search(String keyword) {
        return em.createNamedQuery("Book.search", Book.class)
                .setParameter("keyword", "%" + keyword.toLowerCase() + "%")
                .getResultList();
    }
    
    public long count() {
        return em.createQuery("SELECT COUNT(b) FROM Book b", Long.class)
                .getSingleResult();
    }
    
    public boolean isbnExists(String isbn, Long excludeId) {
        String jpql = "SELECT COUNT(b) FROM Book b WHERE b.isbn = :isbn";
        if (excludeId != null) {
            jpql += " AND b.id != :excludeId";
        }
        
        TypedQuery<Long> query = em.createQuery(jpql, Long.class)
                .setParameter("isbn", isbn);
        
        if (excludeId != null) {
            query.setParameter("excludeId", excludeId);
        }
        
        return query.getSingleResult() > 0;
    }
}