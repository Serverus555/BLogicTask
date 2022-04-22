package me.serverus.blogictask.repository;

import me.serverus.blogictask.repository.interfaces.IAbstractDao;
import me.serverus.blogictask.utils.search.Filter;
import me.serverus.blogictask.utils.search.Sort;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
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
                    predicate = cb.and(predicate, p.get());
                }
            }
            else if (persistenceType == Attribute.PersistentAttributeType.MANY_TO_MANY) {
                Optional<Predicate> p = filterManyToMany(filter, cb, cq);
                if (p.isPresent()) {
                    predicate = cb.and(predicate, p.get());
                }
//                Class<?> subEntitiesClass= ((PluralAttribute<?, ?, ?>) em.getMetamodel().entity(typeClass).getDeclaredAttribute(filter.column)).getElementType().getJavaType();
//                Subquery<?> subQuery = cq.subquery(subEntitiesClass);
//                Root<?> subQueryRoot = subQuery.from(subEntitiesClass);
//                subQuery.where(createFilterSubPredicate(filter.value, cb, subQueryRoot));
//                subQuery.select(subQueryRoot);
//                predicate = cb.and(predicate, cb.in(subQueryRoot).value(subQuery));

            }
            else {
                predicate = cb.and(predicate, createFilterSubPredicate(filter.value, cb, root.get(filter.column)));
            }
        }

        cq.where(predicate);

        if (root.getModel()
                .getDeclaredAttribute(sort.column)
                .getPersistentAttributeType() != Attribute.PersistentAttributeType.MANY_TO_MANY) {

            Function<Expression<?>, Order> orderFunc = sort.isAscend ? cb::asc : cb::desc;
            cq.orderBy(orderFunc.apply(root.get(sort.column)));
        }

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

    protected Predicate createFilterSubPredicate(String value, CriteriaBuilder cb, Path<?> path) {
        Class<?> nestedEntityClass = path.getJavaType();
        EntityType<?> entityType = em.getMetamodel().entity(nestedEntityClass);
        Predicate predicate = cb.disjunction();
        Set<? extends Attribute<?, ?>> a = entityType.getDeclaredAttributes();
        for (Attribute<?, ?> column : a) {
            Optional<Predicate> p = getPredicate(cb, column.getJavaType(), path.get(column.getName()), value);
            if (p.isPresent()) {
                predicate = cb.or(predicate, p.get());
            }
        }
        return predicate;
    }

    protected Optional<Predicate> getPredicate(CriteriaBuilder cb, Class<?> columnClass, Path<?> path, String value) {
        Predicate predicate = null;
        if (columnClass == String.class) {
            predicate = cb.like((Expression<String>) path, "%"+value+"%");
        }
        else if (columnClass == Long.class && value.matches("^[0-9]+$")) {
            predicate = cb.equal(path, Integer.parseInt(value));
        }
        else if (columnClass == Date.class) {
            Date date = null;
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(value);
            }
            catch (ParseException ignored) {}
            if (date != null) {
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                c.add(Calendar.DATE, -1);
                Date after = c.getTime();
                c.add(Calendar.DATE, 2);
                Date before = c.getTime();

//                predicate = cb.greaterThanOrEqualTo((Expression<Date>) path, after)
                predicate = cb.between((Expression<Date>) path, after, before);
            }
        }

        return Optional.ofNullable(predicate);
    }

    protected Optional<Predicate> filterManyToMany(Filter filter, CriteriaBuilder cb, CriteriaQuery<T> cq) {
        return Optional.empty();
    }
}
