package me.serverus.blogictask.repository;

import me.serverus.blogictask.model.Assignment;
import me.serverus.blogictask.repository.interfaces.IAssignmentDao;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.Optional;

@Stateless
public class AssignmentDao extends AbstractDao<Assignment> implements IAssignmentDao {

    public AssignmentDao() {
        super(Assignment.class);
    }

    @Override
    protected Optional<Predicate> getPredicate(CriteriaBuilder cb, Class<?> columnClass, Path<String> path, String value) {
        Predicate predicate = null;
        if (columnClass == Assignment.ControlStatus.class) {
            predicate = cb.equal(path, Assignment.ControlStatus.valueOf(value));
        }
        if (columnClass == Assignment.ExecuteStatus.class) {
            predicate = cb.equal(path, Assignment.ExecuteStatus.valueOf(value));
        }


        if (predicate == null) {
            return super.getPredicate(cb, columnClass, path, value);
        }
        return Optional.of(predicate);
    }
}
