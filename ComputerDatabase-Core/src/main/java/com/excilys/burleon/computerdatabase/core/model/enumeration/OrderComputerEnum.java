package com.excilys.burleon.computerdatabase.core.model.enumeration;

import com.excilys.burleon.computerdatabase.core.model.Computer;

public enum OrderComputerEnum implements IOrderEnum<Computer> {
    INTRODUCE_DATE("computer.introduced"), DISCONTINUE_DATE("computer.discontinued"), NAME("computer.name"),
    COMPANY_NAME("company.name");

    public static OrderComputerEnum fromName(final String name) {
        switch (name) {
            case "computer.name":
                return NAME;
            case "computer.introduced":
                return INTRODUCE_DATE;
            case "computer.discontinued":
                return OrderComputerEnum.DISCONTINUE_DATE;
            case "company.name":
                return COMPANY_NAME;
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
    OrderComputerEnum(final String s) {
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
