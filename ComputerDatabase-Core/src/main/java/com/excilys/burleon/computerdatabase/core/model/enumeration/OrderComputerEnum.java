package com.excilys.burleon.computerdatabase.core.model.enumeration;

import com.excilys.burleon.computerdatabase.core.model.Computer;

public enum OrderComputerEnum implements IOrderEnum<Computer> {
    INTRODUCE_DATE("computer.introduced"), DISCONTINUE_DATE("computer.discontinued"), NAME("computer.name"),
    COMPANY_NAME("company.name");

    private final String name;

    /**
     *
     * @param s
     *            The name
     */
    OrderComputerEnum(final String s) {
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
