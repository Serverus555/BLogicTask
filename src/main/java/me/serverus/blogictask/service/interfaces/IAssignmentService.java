package me.serverus.blogictask.service.interfaces;

import me.serverus.blogictask.dto.AssignmentPutDto;
import me.serverus.blogictask.dto.CompanyPutDto;
import me.serverus.blogictask.model.Assignment;
import me.serverus.blogictask.repository.interfaces.IAssignmentDao;
import me.serverus.blogictask.service.interfaces.IDaoInteractService;

public interface IAssignmentService extends IDaoInteractService<Assignment, IAssignmentDao> {
    void put(AssignmentPutDto dto);
}
