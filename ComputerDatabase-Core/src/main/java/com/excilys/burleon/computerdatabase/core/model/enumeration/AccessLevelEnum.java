package com.excilys.burleon.computerdatabase.core.model.enumeration;

public enum AccessLevelEnum {
    ADMIN("ADMIN"), USER("USER"), ANONYMOUS("ANONYMOUS");

    private final String name;

    /**
     *
     * @param s
     *            The name
     */
    AccessLevelEnum(final String s) {
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
