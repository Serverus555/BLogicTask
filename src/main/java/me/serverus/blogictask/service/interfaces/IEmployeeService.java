package me.serverus.blogictask.service.interfaces;

import me.serverus.blogictask.model.Employee;
import me.serverus.blogictask.repository.interfaces.IEmployeeDao;
import me.serverus.blogictask.service.interfaces.IDaoInteractService;

public interface IEmployeeService extends IDaoInteractService<Employee, IEmployeeDao> {
}
