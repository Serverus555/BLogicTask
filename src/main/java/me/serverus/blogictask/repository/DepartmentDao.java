package me.serverus.blogictask.repository;

import me.serverus.blogictask.model.Department;

import javax.ejb.Stateless;

@Stateless
public class DepartmentDao extends AbstractDao<Department> {

    public DepartmentDao() {
        super(Department.class);
    }

    @Override
    protected String convertColumnName(String column) {
        if ("director".equals(column)) {
            return "director_id";
        }
        return super.convertColumnName(column);
    }
}