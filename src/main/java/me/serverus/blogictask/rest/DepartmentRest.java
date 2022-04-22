package me.serverus.blogictask.rest;

import me.serverus.blogictask.dto.DepartmentPutDto;
import me.serverus.blogictask.model.Department;
import me.serverus.blogictask.service.interfaces.IDepartmentService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("/department")
public class DepartmentRest extends CrudRest<Department, IDepartmentService> {

    @Inject
    public DepartmentRest(IDepartmentService service) {
        super(service);
    }

    @PUT
    @Path("/byDto")
    @Consumes(MediaType.APPLICATION_JSON)
    public void put(@Valid DepartmentPutDto dto) {
        service.put(dto);
    }
}
