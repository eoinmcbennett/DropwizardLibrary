package org.kainos.ea.api;

import org.junit.jupiter.api.Test;
import org.kainos.ea.cli.Book;
import org.kainos.ea.client.*;
import org.kainos.ea.db.IDAO;
import org.kainos.ea.db.TestBookDAO;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookServiceTest {
    private BookService bookService;

    public BookServiceTest(){
        bookService = new BookService(new TestBookDAO());
    }

    @Test
    void testGetAllBooks() {

    }
}
