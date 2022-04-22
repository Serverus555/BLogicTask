package me.serverus.blogictask.repository.interfaces;

import me.serverus.blogictask.model.Employee;

public interface IEmployeeDao extends IAbstractDao<Employee> {
    boolean hasLinks(Employee e);
}
