package com.excilys.burleon.computerdatabase.core.model.enumeration;

import com.excilys.burleon.computerdatabase.core.model.Company;

public enum OrderCompanyEnum implements IOrderEnum<Company> {
    NAME("company.name");

    private final String name;

    /**
     *
     * @param s
     *            The name
     */
    OrderCompanyEnum(final String s) {
        this.name = s;
    }

    /**
     *
     * @param otherName
     *            The orther name
     * @return True or false
     */
    public boolean equalsName(final String otherName) {
        return this.name.equals(otherName);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
