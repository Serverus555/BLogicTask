package me.serverus.blogictask.dto;


import com.sun.istack.Nullable;
import me.serverus.blogictask.model.Company;

public class CompanyPutDto {

    @Nullable
    public Long id;
    public String name;
    public String physicalAddress;
    public String legalAddress;
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
