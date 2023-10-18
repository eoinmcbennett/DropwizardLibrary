package org.kainos.ea.resources;

import io.swagger.annotations.Api;
import org.kainos.ea.api.BookService;
import org.kainos.ea.cli.Book;
import org.kainos.ea.client.*;
import org.kainos.ea.client.NotFoundException;
import org.kainos.ea.db.BookDao;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api("Engineering Academy Library dropwizard api")
@Path("/api")
public class BookController {
    private BookService bookService = new BookService(new BookDao());

    @GET
    @Path("/books")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks() {
        try{
            return Response.ok(bookService.getAllBooks()).build();
        } catch(FailedToFetchException e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/books/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookById(@PathParam("id") int id) {
        try {
            return Response.ok(bookService.getBookById(id)).build();
        } catch (FailedToFetchException e) {
            return Response.serverError().entity(e.getMessage()).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/books/genre/count")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookCountByGenre() {
        try {
            return Response.ok(bookService.getBooksByGenre()).build();
        } catch (FailedToFetchException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/books")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBook(Book book) {
        try {
            return Response.status(Response.Status.CREATED).entity(bookService.createBook(book)).build();
        } catch(FailedToCreateException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/books/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBook(@PathParam("id") int id,Book book) {
        try {
            bookService.updateBook(id,book);
            return Response.ok().build();
        } catch (FailedToUpdateException e) {
            return Response.serverError().entity(e.getMessage()).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/books/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBook(@PathParam("id") int id) {
        try {
            bookService.deleteBook(id);
            return Response.ok().build();
        } catch (FailedToDeleteException e) {
            return Response.serverError().entity(e.getMessage()).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
