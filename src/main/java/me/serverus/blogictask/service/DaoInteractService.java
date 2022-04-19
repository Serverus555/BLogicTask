package me.serverus.blogictask.service;

import me.serverus.blogictask.repository.interfaces.IAbstractDao;
import me.serverus.blogictask.service.interfaces.IDaoInteractService;
import me.serverus.blogictask.utils.Filter;
import me.serverus.blogictask.utils.Sort;
import org.hibernate.HibernateException;

import javax.ejb.Stateless;
import javax.persistence.PersistenceException;
import javax.persistence.TransactionRequiredException;
import java.util.List;

@Stateless
public abstract class DaoInteractService<T, DAO extends IAbstractDao<T>> implements IDaoInteractService<T, DAO> {

    public DAO dao;

    protected DaoInteractService(DAO dao) {
        this.dao = dao;
    }

    @Override
    public boolean put(T obj) {
        try {
            dao.update(obj);
            return true;
        }
        catch (IllegalArgumentException | TransactionRequiredException e) {
            try {
                dao.create(obj);
                return true;
            }
            catch (IllegalArgumentException e2) {
                return false;
            }
        }
    }

    @Override
    public T find(long id) {
        return dao.find(id);
    }


    @Override
    public List<T> getEntities(List<Filter> filters, Sort sort, int from, int count) {
        return dao.getEntities(filters, sort, from, count);
    }


    @Override
    public void delete(long id) {
        dao.deleteById(id);
    }
}
