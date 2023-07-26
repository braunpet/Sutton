package de.fhws.fiw.fds.sutton.server.api.security.api.queries.attribute_value;

import de.fhws.fiw.fds.sutton.server.database.searchParameter.AbstractAttributeLikeValue;

public class AttributeLikeValueUserName extends AbstractAttributeLikeValue {

    public AttributeLikeValueUserName(String equalsValue) {
        super("userName", equalsValue);
    }

}
