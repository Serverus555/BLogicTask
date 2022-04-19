package me.serverus.blogictask.repository.interfaces;

import me.serverus.blogictask.model.Company;
import me.serverus.blogictask.repository.interfaces.IAbstractDao;

import javax.ejb.Local;
import javax.ejb.LocalBean;

public interface ICompanyDao extends IAbstractDao<Company> {
}
