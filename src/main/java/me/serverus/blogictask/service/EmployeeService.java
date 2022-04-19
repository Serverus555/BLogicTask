package me.serverus.blogictask.service;

import me.serverus.blogictask.model.Employee;
import me.serverus.blogictask.repository.interfaces.IEmployeeDao;
import me.serverus.blogictask.service.interfaces.IEmployeeService;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

public class EmployeeService extends DaoInteractService<Employee, IEmployeeDao> implements IEmployeeService {

    @Inject
    public EmployeeService(IEmployeeDao dao) {
        super(dao);
    }
}
