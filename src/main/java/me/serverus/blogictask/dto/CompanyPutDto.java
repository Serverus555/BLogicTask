package me.serverus.blogictask.dto;


import com.sun.istack.Nullable;
import me.serverus.blogictask.model.Company;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CompanyPutDto {

    @Nullable
    public Long id;
    @NotEmpty
    public String name;
    @NotEmpty
    public String physicalAddress;
    @NotEmpty
    public String legalAddress;
    @NotNull
    public Long director;

    public Company createEntity() {
        Company c = new Company();
        c.setId(id);
        c.setName(name);
        c.setPhysicalAddress(physicalAddress);
        c.setLegalAddress(legalAddress);
        return c;
    }
}
