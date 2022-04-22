package me.serverus.blogictask.service;

import me.serverus.blogictask.model.Employee;
import me.serverus.blogictask.repository.interfaces.IEmployeeDao;
import me.serverus.blogictask.service.interfaces.IEmployeeService;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;

public class EmployeeService extends DaoInteractService<Employee, IEmployeeDao> implements IEmployeeService {

    @Inject
    public EmployeeService(IEmployeeDao dao) {
        super(dao);
    }

    @Override
    public void delete(long id) {
        if (dao.hasLinks(dao.find(id))) {
            throw new ConstraintViolationException("linked", null);
        }
        super.delete(id);
    }
}
