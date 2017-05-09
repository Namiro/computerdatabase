package com.excilys.burleon.computerdatabase.webapp.dtomodel;

import com.google.gson.Gson;

public class CompanyDto {
    public String id = "";
    public String name = "";

    public CompanyDto() {

    }

    public CompanyDto(final String id, final String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
