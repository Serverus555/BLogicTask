package me.serverus.blogictask.repository;

import me.serverus.blogictask.model.Employee;
import me.serverus.blogictask.repository.interfaces.IEmployeeDao;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
public class EmployeeDao extends AbstractDao<Employee> implements IEmployeeDao {

    public EmployeeDao() {
        super(Employee.class);
    }
}
