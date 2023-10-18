package org.kainos.ea.resources;

import io.swagger.annotations.Api;
import org.kainos.ea.api.LoanService;
import org.kainos.ea.cli.Book;
import org.kainos.ea.cli.Loan;
import org.kainos.ea.cli.MemberLoan;
import org.kainos.ea.client.FailedToCreateException;
import org.kainos.ea.client.FailedToFetchException;
import org.kainos.ea.client.NotFoundException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Api("Engineering Academy Library dropwizard api")
@Path("/api")
public class LoanController {
    private LoanService loanService = new LoanService();

    @GET
    @Path("/loans")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLoans() {
        try {
            return Response.ok(loanService.getAllLoans()).build();
        } catch (FailedToFetchException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/loans/member/current/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCurrentLoansForMember(@PathParam("id")int memberId) {
        try {
            List<MemberLoan> memberLoans = loanService.getAllLoansForMemberId(memberId);

            return Response.ok(
                    memberLoans.stream().filter(memberLoan -> memberLoan.getLoan().getReturnDate().after(new Date(Instant.now().toEpochMilli()))).collect(Collectors.toList())
            ).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/loans/member/past/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPastLoansForMember(@PathParam("id")int memberId) {
        try {
            List<MemberLoan> memberLoans = loanService.getAllLoansForMemberId(memberId);

            return Response.ok(
                    memberLoans.stream().filter(memberLoan -> memberLoan.getLoan().getReturnDate().before(new Date(Instant.now().toEpochMilli()))).collect(Collectors.toList())
            ).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/loans/member//{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllLoansForMember(@PathParam("id")int memberId) {
        try {
            List<MemberLoan> memberLoans = loanService.getAllLoansForMemberId(memberId);

            return Response.ok(memberLoans).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/loans/publisher/most")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPublisherWithHighestLoans() {
        try {
            Map<String,Integer> publisherLoans = loanService.getPublisherLoans();

            return Response.ok(
                    Collections.max(publisherLoans.entrySet(), Map.Entry.comparingByValue())
            ).build();
        } catch (FailedToFetchException e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/loans/author/least")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAuthorWithLeastLoans() {
        try {
            Map<String,Integer> authorLoans = loanService.getAuthorLoans();

            return Response.ok(
                    Collections.min(authorLoans.entrySet(), Map.Entry.comparingByValue())
            ).build();
        } catch (FailedToFetchException e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/loans/averageprice")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAveragePriceOfLoansBooksWithinYear() {
        try {
            return Response.ok(loanService.getAveragePriceOfBooksLoanedPublishedLastYear()).build();
        } catch (Exception e){
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/loans")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBook(Loan loan) {
        try {
            return Response.status(Response.Status.CREATED).entity(loanService.createLoan(loan)).build();
        } catch(FailedToCreateException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
