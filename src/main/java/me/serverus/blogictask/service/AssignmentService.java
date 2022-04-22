package me.serverus.blogictask.service;

import me.serverus.blogictask.dto.AssignmentPutDto;
import me.serverus.blogictask.model.Assignment;
import me.serverus.blogictask.model.Employee;
import me.serverus.blogictask.repository.interfaces.IAssignmentDao;
import me.serverus.blogictask.service.interfaces.IAssignmentService;
import me.serverus.blogictask.service.interfaces.IEmployeeService;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AssignmentService extends DaoInteractService<Assignment, IAssignmentDao> implements IAssignmentService {

    @Inject
    private IEmployeeService employeeService;

    @Inject
    protected AssignmentService(IAssignmentDao dao) {
        super(dao);
    }

    @Override
    public void put(AssignmentPutDto dto) {
        Employee author = employeeService.find(dto.author);
        if (author == null) {
            throw new ConstraintViolationException("notfound author", null);
        }
        Set<Employee> executors = new HashSet<>();
        for (Long executorId : dto.executors) {
            Employee executor = employeeService.find(executorId);
            if (executor == null) {
                throw new ConstraintViolationException("notfound executors", null);
            }
            executors.add(executor);
        }
        if (!dto.executeStatus.validateControlStatus(dto.controlStatus)) {
            throw new ConstraintViolationException("controlStatus", null);
        }

        Assignment edited = dto.createEntity();
        edited.setExecutors(executors);
        edited.setAuthor(author);

        if (dto.id == null) {
            dao.create(edited);
        }
        else {
            Assignment assignment = dao.find(dto.id);
//            never executed
            if (assignment == null) {
                throw new ConstraintViolationException("id", null);
            }
            dao.update(edited);
        }
    }
}
