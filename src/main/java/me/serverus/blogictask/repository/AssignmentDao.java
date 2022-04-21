package me.serverus.blogictask.repository;

import me.serverus.blogictask.model.Assignment;
import me.serverus.blogictask.model.Employee;
import me.serverus.blogictask.repository.interfaces.IAssignmentDao;
import me.serverus.blogictask.utils.search.Filter;

import javax.ejb.Stateless;
import javax.persistence.criteria.*;
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

    @Override
    protected Optional<Predicate> filterManyToMany(Filter filter, CriteriaBuilder cb, CriteriaQuery<Assignment> cq) {
        Subquery<Employee> subQuery = cq.subquery(Employee.class);
        Root<Employee> root = subQuery.from(Employee.class);
        cq.where(createFilterSubPredicate(filter.value, cb, root));
        subQuery.select(root);
        return Optional.of(cb.in(subQuery.select(root)));
    }
}
