package me.serverus.blogictask.service.interfaces;

import me.serverus.blogictask.dto.DepartmentPutDto;
import me.serverus.blogictask.model.Department;
import me.serverus.blogictask.repository.interfaces.IDepartmentDao;
import me.serverus.blogictask.service.interfaces.IDaoInteractService;

public interface IDepartmentService extends IDaoInteractService<Department, IDepartmentDao> {
    void put(DepartmentPutDto dto);
}
