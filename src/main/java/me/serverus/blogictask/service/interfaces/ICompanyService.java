package me.serverus.blogictask.service.interfaces;

import me.serverus.blogictask.dto.CompanyPutDto;
import me.serverus.blogictask.model.Company;
import me.serverus.blogictask.repository.interfaces.ICompanyDao;

import javax.ejb.LocalBean;

public interface ICompanyService extends IDaoInteractService<Company, ICompanyDao> {
    boolean put(CompanyPutDto dto);
}
