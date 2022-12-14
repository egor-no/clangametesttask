package com.clangame.demo.controllers;

import com.clangame.demo.data.entities.Task;
import com.clangame.demo.data.entities.TaskTransaction;
import com.clangame.demo.exception.TransactionIsNotCommittedException;
import com.clangame.demo.services.TaskService;
import com.clangame.demo.services.TransactionService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/task")
public class TaskController {

    @Inject
    TaskService taskService;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") long id) {
         return Response.ok(taskService.get(id)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.ok(taskService.getAll()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Task task,  @Context UriInfo uriInfo) {
        long id = taskService.save(task);

        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        uriBuilder.path(Long.toString(id));
        return Response.created(uriBuilder.build()).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(Task updatedTask, @PathParam("id") long id) {
        taskService.update(updatedTask);
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}/complete")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response complete(@PathParam("id") long id, @QueryParam("clan") long clanId) {
        try {
            TaskTransaction transaction = taskService.completeTask(clanId, id);
            return Response.ok(transaction).build();
        } catch (TransactionIsNotCommittedException e) {
            return Response.status(503, "База данных не может обработать запрос. Попробуйте ещё раз").build();
        }

    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") long id) {
        taskService.delete(id);
        return Response.noContent().build();
    }

}
