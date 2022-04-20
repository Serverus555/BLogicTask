package me.serverus.blogictask.rest;

import me.serverus.blogictask.dto.AssignmentPutDto;
import me.serverus.blogictask.dto.CompanyPutDto;
import me.serverus.blogictask.model.Assignment;
import me.serverus.blogictask.service.interfaces.IAssignmentService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("/assignment")
public class AssignmentRest extends CrudRest<Assignment, IAssignmentService> {

    @Inject
    public AssignmentRest(IAssignmentService service) {
        super(service);
    }

    @PUT
    @Path("/byDto")
    @Consumes(MediaType.APPLICATION_JSON)
    public void put(@Valid AssignmentPutDto dto) {
        service.put(dto);
    }
}
