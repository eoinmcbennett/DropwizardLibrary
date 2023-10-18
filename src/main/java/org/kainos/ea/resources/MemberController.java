package org.kainos.ea.resources;

import io.swagger.annotations.Api;
import org.kainos.ea.api.MemberService;
import org.kainos.ea.cli.Member;
import org.kainos.ea.client.*;
import org.kainos.ea.client.NotFoundException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api("Engineering Academy Library dropwizard api")
@Path("/api")
public class MemberController {
    private MemberService memberService = new MemberService();

    @GET
    @Path("/members")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMembers() {
        try{
            return Response.ok(memberService.getAllMembers()).build();
        } catch(FailedToFetchException e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/members/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMemberById(@PathParam("id") int id) {
        try {
            return Response.ok(memberService.getMemberById(id)).build();
        } catch (FailedToFetchException e) {
            return Response.serverError().entity(e.getMessage()).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/members")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBook(Member member) {
        try {
            return Response.status(Response.Status.CREATED).entity(memberService.createMember(member)).build();
        } catch(FailedToCreateException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/members/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBook(@PathParam("id") int id,Member member) {
        try {
            memberService.updateMember(id,member);
            return Response.ok().build();
        } catch (FailedToUpdateException e) {
            return Response.serverError().entity(e.getMessage()).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/members/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBook(@PathParam("id") int id) {
        try {
            memberService.deleteMember(id);
            return Response.ok().build();
        } catch (FailedToDeleteException e) {
            return Response.serverError().entity(e.getMessage()).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
