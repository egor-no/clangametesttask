package com.clangame.demo.controllers;

import com.clangame.demo.data.entities.Clan;
import com.clangame.demo.data.entities.Transaction;
import com.clangame.demo.services.ClanService;
import com.clangame.demo.services.TransactionService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("/clan")
public class ClanController {
    @Inject
    ClanService clanService;

    @Inject
    TransactionService transactionService;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") long id) {
        Clan clan = clanService.get(id);
        if (clan != null)
            return Response.ok(clan).build();
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<Clan> clans = clanService.getAll();
        return Response.ok(clans).build();
    }

    @GET
    @Path("/{id}/transactions")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransactions(@PathParam("id") long id) {
        List<Transaction> transactions = transactionService.getAllByClan(id);
        return Response.ok(transactions).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Clan clan, @Context UriInfo uriInfo) {
        long id = clanService.save(clan);

        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        uriBuilder.path(Long.toString(id));
        return Response.created(uriBuilder.build()).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(Clan updatedClan, @PathParam("id") long id) {
        clanService.update(updatedClan);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") long id) {
        clanService.delete(id);
        return Response.noContent().build();
    }
}
