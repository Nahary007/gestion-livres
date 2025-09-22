package com.example.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import org.hibernate.annotations.processing.Pattern;

@Entity
@Table(name= "books")
@NamedQueries({
    @NamedQuery(name = "Book.findAll", query = "SELECT b FROM book b"),
    @NamedQuery(name = "Book.findByTitle", query = "SELECT b FROM book b WHERE LOWER(b.title) LIK LOWER(:title)"),
    @NamedQuery(name = "Book.findByAuthor", query = "SELECT b FROM book b WHERE b.author.id = :authorId"),
    @NamedQuery(name = "Book.search", query = "SELECT b FROM book b WHERE " + "LOWER(b.title) LIKE LOWER(:keyword) OR " + "LOWER(b.author.name) LIKE LOWER(:keyword)")
})

public class Book implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Le titre est obligatoire")
    @Size(min = 1, max = 200, message = "Le titre doit contenir entre 1 et 200 caractères")
    @Column(nullable = false, length = 200)
    private String title;
    
    @NotBlank(message = "L'ISBN est obligatoire")
    @Pattern(regexp = "^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$", 
             message = "ISBN format invalide")
    @Column(unique = true, nullable = false, length = 20)
    private String isbn;
    
    @NotNull(message = "Le prix est obligatoire")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le prix doit être supérieur à 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    
    @Min(value = 0, message = "Le nombre de pages doit être positif")
    @Column(name = "page_count")
    private Integer pageCount;
    
    @PastOrPresent(message = "La date de publication doit être dans le passé ou présent")
    @Column(name = "publication_date")
    private LocalDate publicationDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    @NotNull(message = "L'auteur est obligatoire")
    private Author author;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private BookStatus status;
    
    @Lob
    @Column(length = 1000)
    private String description;
    
    // Constructeurs
    public Book() {
        this.status = BookStatus.AVAILABLE;
    }
    
    public Book(String title, String isbn, BigDecimal price, Author author) {
        this();
        this.title = title;
        this.isbn = isbn;
        this.price = price;
        this.author = author;
    }
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    
    public Integer getPageCount() { return pageCount; }
    public void setPageCount(Integer pageCount) { this.pageCount = pageCount; }
    
    public LocalDate getPublicationDate() { return publicationDate; }
    public void setPublicationDate(LocalDate publicationDate) { this.publicationDate = publicationDate; }
    
    public Author getAuthor() { return author; }
    public void setAuthor(Author author) { this.author = author; }
    
    public BookStatus getStatus() { return status; }
    public void setStatus(BookStatus status) { this.status = status; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    // Méthodes utilitaires
    public String getFormattedPrice() {
        return price != null ? String.format("%,.2f €", price) : "";
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                '}';
    }
    
    public enum BookStatus {
        AVAILABLE, BORROWED, RESERVED, UNDER_MAINTENANCE
    }
}

