package com.excilys.burleon.computerdatabase.console.model;

import com.google.gson.Gson;

public class Company {
    public String id = "";
    public String name = "";

    public Company() {

    }

    public Company(final String id, final String name) {
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
