package me.serverus.blogictask.service;

import me.serverus.blogictask.dto.DepartmentPutDto;
import me.serverus.blogictask.model.Department;
import me.serverus.blogictask.model.Employee;
import me.serverus.blogictask.repository.interfaces.IDepartmentDao;
import me.serverus.blogictask.service.interfaces.IDepartmentService;
import me.serverus.blogictask.service.interfaces.IEmployeeService;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;

public class DepartmentService extends DaoInteractService<Department, IDepartmentDao> implements IDepartmentService {

    @Inject
    private IEmployeeService employeeService;

    @Inject
    protected DepartmentService(IDepartmentDao dao) {
        super(dao);
    }

    @Override
    public void put(DepartmentPutDto dto) {
        Employee director = employeeService.find(dto.director);
        if (director == null) {
            throw new ConstraintViolationException("notfound director", null);
        }

        Department edited = dto.createEntity();
        edited.setDirector(director);

        if (dto.id == null) {
            dao.create(edited);
        }
        else {
            Department department = dao.find(dto.id);
//            never executed
            if (department == null) {
                throw new ConstraintViolationException("id", null);
            }
            dao.update(edited);
        }
    }
}
