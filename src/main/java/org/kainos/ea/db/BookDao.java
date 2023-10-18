package org.kainos.ea.db;

import org.kainos.ea.cli.Book;
import org.kainos.ea.client.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class BookDao implements IDAO<Book> {

    @Override
    public List<Book> getAll() throws FailedToFetchException {
        try {
            Connection conn = DatabaseConnector.getConnection();
            String SQL = "SELECT id, title, author, publisher, isbn, publication_year, genre, available, price FROM Books";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(SQL);

            List<Book> books = new ArrayList<>();

            while(rs.next()){
                books.add(new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("publisher"),
                        rs.getString("isbn"),
                        rs.getDate("publication_year"),
                        rs.getString("genre"),
                        rs.getBoolean("available"),
                        rs.getDouble("price")
                ));
            }

            return books;

        } catch (SQLException e) {
            throw new FailedToFetchException("Failed to fetch all books from the database");
        }
    }

    @Override
    public Book getById(int id) throws FailedToFetchException, NotFoundException {
        try {
            Connection conn = DatabaseConnector.getConnection();
            String SQL = "SELECT id, title, author, publisher, isbn, publication_year, genre, available, price FROM Books WHERE id = '" + id + "'";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(SQL);

            while(rs.next()){
                return new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("publisher"),
                        rs.getString("isbn"),
                        rs.getDate("publication_year"),
                        rs.getString("genre"),
                        rs.getBoolean("available"),
                        rs.getDouble("price")
                );
            }

            throw new NotFoundException("No book with id: " + id + " found");
        } catch (SQLException e) {
            throw new FailedToFetchException("Failed to fetch book with id: " + id + " from the database");
        }
    }

    @Override
    public int create(Book book) throws FailedToCreateException {
        try {
            Connection conn = DatabaseConnector.getConnection();
            String SQL = "INSERT INTO Books(title,author,publisher,isbn,publication_year,genre,available,price) VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement st = conn.prepareStatement(SQL,Statement.RETURN_GENERATED_KEYS);


            st.setString(1,book.getTitle());
            st.setString(2,book.getAuthor());
            st.setString(3,book.getAuthor());
            st.setString(4,book.getIsbn());
            st.setInt(5,book.getPublicationYear().getYear() + 1900);
            st.setString(6,book.getGenre());
            st.setBoolean(7,book.isAvailable());
            st.setDouble(8,book.getPrice());

            st.executeUpdate();

            ResultSet rs = st.getGeneratedKeys();

            if(rs.next()){
                return rs.getInt(1);
            }

            return -1;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToCreateException("Failed to create book with data: " + book.toString());
        }

    }

    @Override
    public void update(int id, Book book) throws FailedToUpdateException {
        try {
            Connection conn = DatabaseConnector.getConnection();
            String SQL = "UPDATE Books SET title = ?,author = ?,publisher = ?,isbn = ?, publication_year = ?, genre = ?,available = ?, price = ? WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(SQL,Statement.RETURN_GENERATED_KEYS);

            st.setString(1,book.getTitle());
            st.setString(2,book.getAuthor());
            st.setString(3,book.getAuthor());
            st.setString(4,book.getIsbn());
            st.setInt(5,book.getPublicationYear().getYear() + 1900);
            st.setString(6,book.getGenre());
            st.setBoolean(7,book.isAvailable());
            st.setDouble(8,book.getPrice());
            st.setInt(9,id);

            st.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToUpdateException("Failed to update book with id: " + id);
        }
    }

    @Override
    public void delete(int id) throws FailedToDeleteException {
        try {
            Connection c = DatabaseConnector.getConnection();
            String deleteStatement = "DELETE FROM Books WHERE id = ?";

            PreparedStatement ps = c.prepareStatement(deleteStatement);

            ps.setInt(1,id);

            ps.executeUpdate();
        } catch(SQLException e){
            throw new FailedToDeleteException("Failed to delete book with id: " + id);
        }
    }
}
