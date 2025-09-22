package com.example.controller;

import com.example.entity.Book;
import com.example.entity.Author;
import com.example.entity.Book.BookStatus;
import com.example.Service.BookService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@ViewScoped
public class BookController implements Serializable {
    
    private static final Logger logger = Logger.getLogger(BookController.class.getName());
    
    @Inject
    private BookService bookService;
    
    private Book book;
    private List<Book> books;
    private List<Book> filteredBooks;
    private String searchKeyword;
    
    private int currentPage = 1;
    private int pageSize = 10;
    private long totalBooks;
    
    private List<Author> authors;
    private BookStatus[] statuses = BookStatus.values();
    
    @PostConstruct
    public void init() {
        book = new Book();
        loadBooks();
        loadAuthors();
    }
    
    public void loadBooks() {
        try {
            books = bookService.findAll(currentPage, pageSize);
            totalBooks = bookService.count();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erreur lors du chargement des livres", e);
            addMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Impossible de charger les livres");
        }
    }
    
    public void loadAuthors() {
        // Implémenter la logique pour charger les auteurs
        // authors = authorService.findAll();
    }
    
    public void search() {
        if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
            books = bookService.search(searchKeyword);
            filteredBooks = books;
        } else {
            loadBooks();
            filteredBooks = null;
        }
    }
    
    public void prepareCreate() {
        book = new Book();
        book.setPrice(BigDecimal.ZERO);
    }
    
    public void prepareEdit(Long id) {
        book = bookService.findById(id);
    }
    
    public void save() {
        try {
            // Validation de l'ISBN
            if (bookService.isbnExists(book.getIsbn(), book.getId())) {
                addMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Cet ISBN est déjà utilisé");
                return;
            }
            
            bookService.create(book);
            loadBooks();
            
            addMessage(FacesMessage.SEVERITY_INFO, "Succès", "Livre enregistré avec succès");
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erreur lors de l'enregistrement du livre", e);
            addMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Erreur lors de l'enregistrement: " + e.getMessage());
        }
    }
    
    public void delete(Long id) {
        try {
            bookService.delete(id);
            loadBooks();
            
            addMessage(FacesMessage.SEVERITY_INFO, "Succès", "Livre supprimé avec succès");
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erreur lors de la suppression du livre", e);
            addMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Erreur lors de la suppression: " + e.getMessage());
        }
    }
    
    private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(severity, summary, detail));
    }
    
    // Getters et Setters
    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }
    
    public List<Book> getBooks() { 
        return filteredBooks != null ? filteredBooks : books; 
    }
    
    public String getSearchKeyword() { return searchKeyword; }
    public void setSearchKeyword(String searchKeyword) { this.searchKeyword = searchKeyword; }
    
    public int getCurrentPage() { return currentPage; }
    public void setCurrentPage(int currentPage) { this.currentPage = currentPage; }
    
    public int getPageSize() { return pageSize; }
    public void setPageSize(int pageSize) { this.pageSize = pageSize; }
    
    public long getTotalBooks() { return totalBooks; }
    
    public int getTotalPages() {
        return (int) Math.ceil((double) totalBooks / pageSize);
    }
    
    public List<Author> getAuthors() { return authors; }
    public BookStatus[] getStatuses() { return statuses; }
}