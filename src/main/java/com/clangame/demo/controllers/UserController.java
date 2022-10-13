package com.clangame.demo.controllers;

import com.clangame.demo.data.entities.*;
import com.clangame.demo.exception.TransactionIsNotCommittedException;
import com.clangame.demo.services.UserAddGoldService;
import com.clangame.demo.services.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("/users")
public class UserController {

    @Inject
    UserAddGoldService addGoldService;

    @Inject
    UserService userService;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") long id) {
        User user = userService.get(id);
        if (user != null)
            return Response.ok(user).build();
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<User> users = userService.getAll();
        return Response.ok(users).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(User user, @Context UriInfo uriInfo) {
        long id = userService.save(user);

        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        uriBuilder.path(Long.toString(id));
        return Response.created(uriBuilder.build()).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(User updatedUser, @PathParam("id") long id) {
        userService.update(updatedUser);
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}/addmoney")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addmoney(@PathParam("id") long id,
                             @QueryParam("clan") long clanId,
                             @QueryParam("gold") int gold) {
        try {
            UserAddGoldTransaction transaction = addGoldService.addGoldToClan(id, clanId, gold);
            return Response.ok(transaction).build();
        } catch (TransactionIsNotCommittedException e) {
            return Response.status(503, "База данных не может обработать запрос. Попробуйте ещё раз").build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") long id) {
        userService.delete(id);
        return Response.noContent().build();
    }
}
