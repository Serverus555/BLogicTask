package me.serverus.blogictask.repository.interfaces;

import me.serverus.blogictask.utils.Filter;
import me.serverus.blogictask.utils.Sort;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import java.util.List;

@Local
public interface IAbstractDao<T> {
    T create(T obj);

    T find(long id);

    List<T> getEntities(List<Filter> filters, Sort sort, int from, int count);

    T update(T obj);

    void deleteById(long id);

    void delete(T obj);
}
