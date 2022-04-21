package me.serverus.blogictask.service.interfaces;

import me.serverus.blogictask.repository.interfaces.IAbstractDao;
import me.serverus.blogictask.utils.search.Filter;
import me.serverus.blogictask.utils.search.Sort;

import javax.ejb.Local;
import java.util.List;

@Local
public interface IDaoInteractService<T, DAO extends IAbstractDao<T>> {
    void put(T obj);

    T find(long id);

    List<T> getEntities(List<Filter> filters, Sort sort, int from, int count);

    void delete(long id);
}
