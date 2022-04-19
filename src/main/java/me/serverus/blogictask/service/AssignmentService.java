package me.serverus.blogictask.service;

import me.serverus.blogictask.dto.AssignmentPutDto;
import me.serverus.blogictask.model.Assignment;
import me.serverus.blogictask.model.Employee;
import me.serverus.blogictask.repository.interfaces.IAssignmentDao;
import me.serverus.blogictask.service.interfaces.IAssignmentService;
import me.serverus.blogictask.service.interfaces.IEmployeeService;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

public class AssignmentService extends DaoInteractService<Assignment, IAssignmentDao> implements IAssignmentService {

    @Inject
    private IEmployeeService employeeService;

    @Inject
    protected AssignmentService(IAssignmentDao dao) {
        super(dao);
    }

    @Override
    public boolean put(AssignmentPutDto dto) {
        Employee author = employeeService.find(dto.author);
        if (author == null) {
            return false;
        }
        Set<Employee> executors = new HashSet<>();
        for (Long executorId : dto.executors) {
            Employee executor = employeeService.find(executorId);
            if (executor == null) {
                return false;
            }
            executors.add(executor);
        }
        if (!dto.executeStatus.validateControlStatus(dto.controlStatus)) {
            return false;
        }

        Assignment edited = dto.createEntity();
        edited.setExecutors(executors);
        edited.setAuthor(author);

        if (dto.id == null) {
            dao.create(edited);
        }
        else {
            Assignment assignment = dao.find(dto.id);
            if (assignment == null) {
                return false;
            }
            dao.update(edited);
        }
        return true;
    }
}
