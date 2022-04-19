package me.serverus.blogictask.repository;

import me.serverus.blogictask.model.Assignment;
import me.serverus.blogictask.repository.interfaces.IAssignmentDao;

import javax.ejb.Stateless;

@Stateless
public class AssignmentDao extends AbstractDao<Assignment> implements IAssignmentDao {

    public AssignmentDao() {
        super(Assignment.class);
    }
}
