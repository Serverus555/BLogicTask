package me.serverus.blogictask.repository;

import me.serverus.blogictask.repository.interfaces.IAbstractDao;
import me.serverus.blogictask.utils.Filter;
import me.serverus.blogictask.utils.Sort;
import org.hibernate.query.criteria.internal.OrderImpl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.function.Function;

public abstract class AbstractDao<T> implements IAbstractDao<T> {

    @PersistenceContext
    protected EntityManager em;
    protected final Class<T> typeClass;

    protected AbstractDao(Class<T> typeClass) {
        this.typeClass = typeClass;
    }

    @Override
    public T create(T obj) {
        em.persist(obj);
        return obj;
    }

    @Override
    public T find(long id) {
        return em.find(typeClass, id);
    }

    @Override
    public List<T> getEntities(List<Filter> filters, Sort sort, int from, int count) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = em.getCriteriaBuilder().createQuery(typeClass);
        Root<T> root = cq.from(typeClass);
        Predicate predicate = cb.conjunction();
        for (Filter filter : filters) {
            cb.and(predicate, cb.equal(root.get(convertColumnName(filter.column)), filter.value));
        }

        cq.where(predicate);

        Function<Expression<?>, Order> orderFunc = sort.isAscend ? cb::asc : cb::desc;
        cq.orderBy(orderFunc.apply(root.get(convertColumnName(sort.column))));

        return em.createQuery(cq)
                .setFirstResult(from)
                .setMaxResults(count)
                .getResultList();
    }

    @Override
    public T update(T obj) {
        return em.merge(obj);
    }

    @Override
    public void deleteById(long id) {
        delete(find(id));
    }

    @Override
    public void delete(T obj) {
        em.remove(obj);
    }

    /**
     * Convert column name for work with jpa naming. Can change name to another like "someColumn" -> "some_column_id"
     * @param column camelCase
     * @return snake_case
     */
    protected String convertColumnName(String column) {
        return column.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
    }
}
