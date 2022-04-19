package me.serverus.blogictask.repository;

import me.serverus.blogictask.model.Company;
import me.serverus.blogictask.repository.interfaces.ICompanyDao;

import javax.ejb.Stateless;
import java.util.Locale;

@Stateless
public class CompanyDao extends AbstractDao<Company> implements ICompanyDao {

    public CompanyDao() {
        super(Company.class);
    }
}

