package me.serverus.blogictask.repository.interfaces;

import me.serverus.blogictask.model.Assignment;
import me.serverus.blogictask.model.Employee;
import me.serverus.blogictask.repository.interfaces.IAbstractDao;

import javax.ejb.Local;
import javax.ejb.LocalBean;

public interface IAssignmentDao extends IAbstractDao<Assignment> {
}
