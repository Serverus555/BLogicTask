package me.serverus.blogictask.repository.interfaces;

import me.serverus.blogictask.utils.search.Filter;
import me.serverus.blogictask.utils.search.Sort;

import javax.ejb.Local;
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
