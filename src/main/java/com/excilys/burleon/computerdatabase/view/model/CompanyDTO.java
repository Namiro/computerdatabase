package com.excilys.burleon.computerdatabase.view.model;

import com.google.gson.Gson;

public class CompanyDTO {
    public String id = "";
    public String name = "";

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
