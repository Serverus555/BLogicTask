package me.serverus.blogictask.repository;

import me.serverus.blogictask.model.Assignment;
import me.serverus.blogictask.model.Company;
import me.serverus.blogictask.model.Department;
import me.serverus.blogictask.model.Employee;
import me.serverus.blogictask.repository.interfaces.IEmployeeDao;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.criteria.*;

@Stateless
public class EmployeeDao extends AbstractDao<Employee> implements IEmployeeDao {

    public EmployeeDao() {
        super(Employee.class);
    }


//    @Override
//    public boolean hasAssignments(Employee e) {
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Assignment> cq = cb.createQuery(Assignment.class);
//        Root<Assignment> root = cq.from(Assignment.class);
//        Predicate predicate = cb.or(cb.equal(root.get("author"), e), cb.isMember(e, root.get("executors")));
//        cq.where(predicate);
//        return em.createQuery(cq).setMaxResults(1).getResultList().size() != 0;
//    }

    @Override
    public boolean hasLinks(Employee e) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Assignment> cq = cb.createQuery(Assignment.class);
        Subquery<Company> companySub = cq.subquery(Company.class);
        Subquery<Department> departmentSub = cq.subquery(Department.class);


        Root<Company> companyRoot = companySub.from(Company.class);
        Predicate companyPredicate = cb.equal(companyRoot.get("director"), e);
        companySub.where(companyPredicate);

        Root<Department> departmentRoot = departmentSub.from(Department.class);
        Predicate departmentPredicate = cb.equal(departmentRoot.get("director"), e);
        departmentSub.where(departmentPredicate);

        companySub.select(companyRoot);
        departmentSub.select(departmentRoot);


        Root<Assignment> assignmentRoot = cq.from(Assignment.class);
        Predicate assignmentPredicate = cb.or(cb.equal(assignmentRoot.get("author"), e), cb.isMember(e, assignmentRoot.get("executors")));
        assignmentPredicate = cb.or(assignmentPredicate, cb.isNotNull(companySub), cb.isNotNull(departmentSub));
        cq.where(assignmentPredicate);

        return em.createQuery(cq).setMaxResults(1).getResultList().size() != 0;
    }
}
