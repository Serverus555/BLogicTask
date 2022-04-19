package me.serverus.blogictask.service.interfaces;

import me.serverus.blogictask.repository.interfaces.IAbstractDao;
import me.serverus.blogictask.utils.Filter;
import me.serverus.blogictask.utils.Sort;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import java.util.List;

@Local
public interface IDaoInteractService<T, DAO extends IAbstractDao<T>> {
    boolean put(T obj);

    T find(long id);

    List<T> getEntities(List<Filter> filters, Sort sort, int from, int count);

    void delete(long id);
}
