package me.serverus.blogictask.rest;

import me.serverus.blogictask.model.Department;
import me.serverus.blogictask.service.interfaces.IDepartmentService;

import javax.inject.Inject;

public class DepartmentRest extends CrudRest<Department, IDepartmentService> {

    @Inject
    public DepartmentRest(IDepartmentService service) {
        super(service);
    }
}
