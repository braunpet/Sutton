package de.fhws.fiw.fds.suttondemoHibernate.server.api.queries.searchParameterAttributesEqualsValues;

import de.fhws.fiw.fds.sutton.server.database.searchParameter.AbstractAttributeEqualsValue;

public class AttributeEqualsValueFirstName extends AbstractAttributeEqualsValue<String> {

    public AttributeEqualsValueFirstName(String searchByValue) {
        super("firstName", searchByValue);
    }

}
