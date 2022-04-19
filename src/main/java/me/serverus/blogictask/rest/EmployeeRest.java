package me.serverus.blogictask.rest;

import me.serverus.blogictask.model.Employee;
import me.serverus.blogictask.service.interfaces.IEmployeeService;

import javax.inject.Inject;
import javax.ws.rs.Path;

@Path("/employee")
public class EmployeeRest extends CrudRest<Employee, IEmployeeService> {

    @Inject
    public EmployeeRest(IEmployeeService service) {
        super(service);
    }
}
