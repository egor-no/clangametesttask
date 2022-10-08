package com.clangame.demo.controllers;

import com.clangame.demo.data.entities.Clan;
import com.clangame.demo.services.ClanService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/clan")
public class ClanController {
    @Inject
    ClanService clanService;

    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, World!";
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") long id) {
        Clan clan = clanService.get(id);
        if (clan != null)
            return Response.ok(clan).build();
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
