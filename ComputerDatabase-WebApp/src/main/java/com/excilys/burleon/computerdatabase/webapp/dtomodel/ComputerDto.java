package com.excilys.burleon.computerdatabase.webapp.dtomodel;

import com.google.gson.Gson;

public class ComputerDto {
    public String id = "";
    public String name = "";
    public String introduced = "";
    public String discontinued = "";
    public CompanyDto company;

    public CompanyDto getCompany() {
        return this.company;
    }

    public String getDiscontinued() {
        return this.discontinued;
    }

    public String getId() {
        return this.id;
    }

    public String getIntroduced() {
        return this.introduced;
    }

    public String getName() {
        return this.name;
    }

    public void setCompany(final CompanyDto company) {
        this.company = company;
    }

    public void setDiscontinued(final String discontinued) {
        this.discontinued = discontinued;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public void setIntroduced(final String introduced) {
        this.introduced = introduced;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
