package com.excilys.burleon.computerdatabase.core.model.enumeration;

import com.excilys.burleon.computerdatabase.core.model.Company;

public enum OrderCompanyEnum implements IOrderEnum<Company> {
    NAME("company.name");

    public static OrderCompanyEnum fromName(final String name) {
        switch (name) {
            case "company.name":
                return NAME;
            default:
                throw new IllegalArgumentException(name + "doesn't match with one of the enum");
        }
    }

    private final String value;

    /**
     *
     * @param s
     *            The value
     */
    OrderCompanyEnum(final String s) {
        this.value = s;
    }

    /**
     *
     * @param otherName
     *            The orther value
     * @return True or false
     */
    public boolean equalsName(final String otherName) {
        return this.value.equals(otherName);
    }

    @Override
    public String toString() {
        return this.value;
    }
}
