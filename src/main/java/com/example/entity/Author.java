package com.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "authors")
@NamedQueries({
    @NamedQuery(name = "Author.findAll", query = "SELECT a FROM Author a ORDER BY a.lastName, a.firstName"),
    @NamedQuery(name = "Author.findByName", query = "SELECT a FROM Author a WHERE " +
            "LOWER(a.firstName) LIKE LOWER(:name) OR " +
            "LOWER(a.lastName) LIKE LOWER(:name)")
})
public class Author implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Le prénom est obligatoire")
    @Size(min = 1, max = 50, message = "Le prénom doit contenir entre 1 et 50 caractères")
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;
    
    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 1, max = 50, message = "Le nom doit contenir entre 1 et 50 caractères")
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;
    
    @Email(message = "L'email doit être valide")
    @Column(unique = true, length = 100)
    private String email;
    
    @Past(message = "La date de naissance doit être dans le passé")
    @Column(name = "birth_date")
    private LocalDate birthDate;
    
    @Column(length = 500)
    private String biography;
    
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books = new ArrayList<>();
    
    // Constructeurs
    public Author() {}
    
    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    
    public String getBiography() { return biography; }
    public void setBiography(String biography) { this.biography = biography; }
    
    public List<Book> getBooks() { return books; }
    public void setBooks(List<Book> books) { this.books = books; }
    
    // Méthodes utilitaires
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    public void addBook(Book book) {
        books.add(book);
        book.setAuthor(this);
    }
    
    public void removeBook(Book book) {
        books.remove(book);
        book.setAuthor(null);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(id, author.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}