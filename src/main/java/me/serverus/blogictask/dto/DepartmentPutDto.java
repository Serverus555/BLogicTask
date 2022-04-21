package me.serverus.blogictask.dto;

import com.sun.istack.Nullable;
import me.serverus.blogictask.model.Department;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class DepartmentPutDto {

    @Nullable
    public Long id;
    @NotEmpty
    public String name;
    @NotEmpty
    public String contacts;
    @NotNull
    public Long director;

    public Department createEntity() {
        Department d = new Department();
        d.setId(id);
        d.setName(name);
        d.setContacts(contacts);
        return d;
    }
}
