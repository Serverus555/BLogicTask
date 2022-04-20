package me.serverus.blogictask.repository;

import me.serverus.blogictask.repository.interfaces.IAbstractDao;
import me.serverus.blogictask.utils.Filter;
import me.serverus.blogictask.utils.Sort;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
            Attribute.PersistentAttributeType persistenceType = root.getModel()
                    .getDeclaredAttribute(filter.column).getPersistentAttributeType();

            if (persistenceType == Attribute.PersistentAttributeType.BASIC) {
                Path<String> path = root.get(filter.column);
                Optional<Predicate> p = getPredicate(cb, path.getJavaType(), path ,filter.value);
                if (p.isPresent()) {
                    predicate = cb.and(p.get());
                }
            }
            else {
                predicate = cb.and(createFilterSubPredicate(filter, cb, root));
            }
        }

        cq.where(predicate);

        Function<Expression<?>, Order> orderFunc = sort.isAscend ? cb::asc : cb::desc;
        cq.orderBy(orderFunc.apply(root.get(sort.column)));

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
     *
     * @param column camelCase
     * @return snake_case
     */
    protected String convertColumnName(String column) {
        return column.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
    }

    private Predicate createFilterSubPredicate(Filter filter, CriteriaBuilder cb, Path<?> path) {
        Class<?> nestedEntityClass = path.getJavaType();
        EntityType<?> entityType = em.getMetamodel().entity(nestedEntityClass);
        Predicate predicate = cb.disjunction();
        Set<? extends Attribute<?, ?>> a = entityType.getDeclaredAttributes();
        for (Attribute<?, ?> column : a) {
            Optional<Predicate> p = getPredicate(cb, column.getJavaType(), path.get(column.getName()), filter.value);
            if (p.isPresent()) {
                predicate = cb.or(p.get());
            }
        }
        return predicate;
    }

    protected Optional<Predicate> getPredicate(CriteriaBuilder cb, Class<?> columnClass, Path<String> path, String value) {
        Predicate predicate = null;
        if (columnClass == String.class) {
            predicate = cb.like(path, "%"+value+"%");
        }
        else if (columnClass == Long.class && value.matches("[0-9]")) {
            predicate = cb.equal(path, value);
        }

        return Optional.ofNullable(predicate);
    }
}
