package org.kainos.ea.cli;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;

public class Book {
    @JsonIgnore
    private int bookId;
    private String title;
    private String author;
    private String publisher;
    private String isbn;
    private Date publicationYear;
    private String genre;
    private boolean available;
    private double price;

    @JsonCreator
    public Book(
            @JsonProperty("title") String title,
            @JsonProperty("author") String author,
            @JsonProperty("publisher") String publisher,
            @JsonProperty("isbn") String isbn,
            @JsonProperty("publicationYear") Date publicationYear,
            @JsonProperty("genre") String genre,
            @JsonProperty("available") boolean available,
            @JsonProperty("price") double price) {
        this.bookId = -1;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.genre = genre;
        this.available = available;
        this.price = price;
    }

    public Book(int bookId,String title, String author, String publisher,String isbn, Date publicationYear, String genre, boolean available, double price) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.genre = genre;
        this.available = available;
        this.price = price;
    }

    @JsonProperty
    public int getBookId(){
        return this.bookId;
    }

    @JsonProperty
    public String getTitle() {
        return title;
    }

    @JsonProperty
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty
    public String getAuthor() {
        return author;
    }

    @JsonProperty
    public void setAuthor(String author) {
        this.author = author;
    }

    @JsonProperty
    public String getPublisher() {
        return publisher;
    }

    @JsonProperty
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @JsonProperty
    public String getIsbn() {
        return isbn;
    }

    @JsonProperty
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @JsonProperty
    public Date getPublicationYear() {
        return publicationYear;
    }

    @JsonProperty
    public void setPublicationYear(Date publicationYear) {
        this.publicationYear = publicationYear;
    }

    @JsonProperty
    public String getGenre() {
        return genre;
    }

    @JsonProperty
    public void setGenre(String genre) {
        this.genre = genre;
    }

    @JsonProperty
    public boolean isAvailable() {
        return available;
    }

    @JsonProperty
    public void setAvailable(boolean available) {
        this.available = available;
    }

    @JsonProperty
    public double getPrice() {
        return price;
    }

    @JsonProperty
    public void setPrice(double price) {
        this.price = price;
    }

    public String toString(){
        return "Id: " + this.bookId;
    }
}
