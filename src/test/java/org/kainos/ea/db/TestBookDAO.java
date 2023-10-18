package org.kainos.ea.db;

import org.kainos.ea.cli.Book;
import org.kainos.ea.client.*;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestBookDAO implements IDAO<Book> {

    private List<Book> books = new ArrayList<>();

    public TestBookDAO(){
        Date date = new Date(Instant.now().toEpochMilli());
        books.add(new Book(1,"Test Book 1","Test Author","Test Publisher","TESTISBN",date,"Test Genre",true,100.99));
        books.add(new Book(2,"Test Book 2","Test Author","Test Publisher","TESTISBN1",date,"Test Genre 1",false,10.99));
        books.add(new Book(3,"Test Book 3","Test Author 2","Test Publisher 2","TESTISBN2",date,"Test Genre 2",true,5.99));
    }

    @Override
    public List<Book> getAll() throws FailedToFetchException {
        return books;
    }

    @Override
    public Book getById(int id) throws FailedToFetchException, NotFoundException {
        Book foundBook = books.stream().filter(book -> book.getBookId() == id).collect(Collectors.toList()).get(0);

        if(foundBook == null){
            throw new NotFoundException("Book by id " + id + " is not found");
        }

        return foundBook;
    }

    @Override
    public int create(Book obj) throws FailedToCreateException {
        if(obj.getBookId() <= 0){
            throw new FailedToCreateException("Failed to create book with allready valid identifier");
        }

        final int newId = books.get(books.size() - 1).getBookId() + 1;
        Book insertedBook = new Book(newId,obj.getTitle(),obj.getAuthor(),obj.getPublisher(),obj.getIsbn(),obj.getPublicationYear(),obj.getGenre(),obj.isAvailable(),obj.getPrice());

        if(!books.add(insertedBook)){
            throw new FailedToCreateException("Failed to add book");
        }

        return newId;
    }

    @Override
    public void update(int id, Book obj) throws FailedToUpdateException {
        try {
            Book book = this.getById(id);
            book.setAuthor(obj.getAuthor());
            book.setTitle(obj.getTitle());
            book.setPublisher(obj.getPublisher());
            book.setIsbn(obj.getIsbn());
            book.setPublicationYear(obj.getPublicationYear());
            book.setPublisher(obj.getPublisher());
            book.setPrice(obj.getPrice());
            book.setAvailable(obj.isAvailable());
        } catch (FailedToFetchException | NotFoundException e) {
            throw new FailedToUpdateException("Book with id " + id + " does not exist");
        }
    }

    @Override
    public void delete(int id) throws FailedToDeleteException {
        if(!books.removeIf(book -> book.getBookId() == id)){
            throw new FailedToDeleteException("Book with id " + id + " does not exist");
        }
    }
}