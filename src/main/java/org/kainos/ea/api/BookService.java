package org.kainos.ea.api;

import org.kainos.ea.cli.Book;
import org.kainos.ea.client.*;
import org.kainos.ea.db.BookDao;
import org.kainos.ea.db.IDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookService {
    private final IDAO<Book> bookDao;

    public BookService(IDAO<Book> bookDao){
        this.bookDao = bookDao;
    }

    public List<Book> getAllBooks() throws FailedToFetchException {
        return bookDao.getAll();
    }

    public Book getBookById(int id) throws FailedToFetchException, NotFoundException {
        Book book = bookDao.getById(id);

        if(book == null){
            throw new NotFoundException("Book with id " + id + " not found");
        }

        return book;
    }

    public int createBook(Book book) throws FailedToCreateException {
        if(book.getBookId() > 0){
            throw new FailedToCreateException("Attempted to recreate book with established id: " + book.getBookId());
        }
        return bookDao.create(book);
    }

    public void updateBook(int id,Book book) throws FailedToUpdateException, NotFoundException {
        try {
            if(bookDao.getById(id) == null){
                throw new NotFoundException("Book with id " + id + " was not found");
            }
        } catch (FailedToFetchException e){
            throw new FailedToUpdateException(e.getMessage());
        }
        bookDao.update(id,book);
    }

    public void deleteBook(int id) throws FailedToDeleteException, NotFoundException {
        try {
            if(bookDao.getById(id) == null) {
                throw new NotFoundException("Book with id " + id + " not found");
            }
            bookDao.delete(id);
        } catch (FailedToFetchException e) {
            throw new FailedToDeleteException(e.getMessage());
        }
    }

    public Map<String,Integer> getBooksByGenre() throws FailedToFetchException {
        try {
            Map<String,Integer> booksByGenre = new HashMap<>();
            List<Book> books = bookDao.getAll();

            for(Book book: books){
                booksByGenre.put(book.getGenre(),booksByGenre.getOrDefault(book.getGenre(),0) + 1);
            }

            return booksByGenre;
        } catch (FailedToFetchException e) {
            throw new FailedToFetchException(e.getMessage());
        }
    }
}
