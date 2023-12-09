package de.fhws.fiw.fds.sutton.server.database.searchParameter;

/**
 * This class provides an {@link AbstractAttributeEqualsValue}, which can be used to search for the given attribute with the given value.
 *
 * @param <ValueType> of the {@link AbstractAttributeEqualsValue#equalsValue}
 */
public abstract class AbstractAttributeEqualsValue<ValueType> {

    /**
     * The name of the Attribute which should searched for
     */
    private String searchByAttribute;

    /**
     * The value which should be equal
     */
    private ValueType equalsValue;

    public AbstractAttributeEqualsValue(String searchByAttribute, ValueType equalsValue) {
        this.searchByAttribute = searchByAttribute;
        this.equalsValue = equalsValue;
    }

    public String getSearchByAttribute() {
        return searchByAttribute;
    }

    public void setSearchByAttribute(String searchByAttribute) {
        this.searchByAttribute = searchByAttribute;
    }

    public ValueType getEqualsValue() {
        return equalsValue;
    }

    public void setEqualsValue(ValueType equalsValue) {
        this.equalsValue = equalsValue;
    }

}
