package me.serverus.blogictask.rest;

import me.serverus.blogictask.dto.GetEntitiesRequest;
import me.serverus.blogictask.service.interfaces.IDaoInteractService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

public abstract class CrudRest<T, SERVICE extends IDaoInteractService<T, ?>> {

    protected SERVICE service;

    protected CrudRest(SERVICE service) {
        this.service = service;
    }

    private CrudRest() {}

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean put(T obj) {
        return service.put(obj);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public T get(@PathParam("id") long id) {
        return service.find(id);
    }

    @POST
    @Path("/entities/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<T> getEntities(GetEntitiesRequest request) {
        return service.getEntities(request.filters, request.sort, request.from, request.count);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") long id) {
        service.delete(id);
    }
}
