package me.serverus.blogictask.rest;

import me.serverus.blogictask.dto.CompanyPutDto;
import me.serverus.blogictask.model.Company;
import me.serverus.blogictask.model.Employee;
import me.serverus.blogictask.service.CompanyService;
import me.serverus.blogictask.service.DaoInteractService;
import me.serverus.blogictask.service.interfaces.ICompanyService;

import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("/company")
public class CompanyRest extends CrudRest<Company, ICompanyService> {

    @Inject
    public CompanyRest(ICompanyService service) {
        super(service);
    }

    @PUT
    @Path("/byDto")
    @Consumes(MediaType.APPLICATION_JSON)
    public void put(@Valid CompanyPutDto dto) {
        service.put(dto);
    }
}
