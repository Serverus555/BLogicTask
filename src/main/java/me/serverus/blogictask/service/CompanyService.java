package me.serverus.blogictask.service;

import me.serverus.blogictask.dto.CompanyPutDto;
import me.serverus.blogictask.model.Company;
import me.serverus.blogictask.model.Employee;
import me.serverus.blogictask.repository.interfaces.ICompanyDao;
import me.serverus.blogictask.service.interfaces.ICompanyService;
import me.serverus.blogictask.service.interfaces.IEmployeeService;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;

public class CompanyService extends DaoInteractService<Company, ICompanyDao> implements ICompanyService {

    @Inject
    private IEmployeeService employeeService;

    @Inject
    public CompanyService(ICompanyDao dao) {
        super(dao);
    }

    @Override
    public void put(CompanyPutDto dto) {
        Employee director = employeeService.find(dto.director);
        if (director == null) {
            throw new ConstraintViolationException("director", null);
        }

        Company edited = dto.createEntity();
        edited.setDirector(director);

        if (dto.id == null) {
            dao.create(edited);
        }
        else {
            Company company = dao.find(dto.id);
            if (company == null) {
                throw new ConstraintViolationException("id", null);
            }
            dao.update(edited);
        }
    }
}
