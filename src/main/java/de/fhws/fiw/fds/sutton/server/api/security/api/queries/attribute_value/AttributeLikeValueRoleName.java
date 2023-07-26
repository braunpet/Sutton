package de.fhws.fiw.fds.sutton.server.api.security.api.queries.attribute_value;

import de.fhws.fiw.fds.sutton.server.database.searchParameter.AbstractAttributeLikeValue;

public class AttributeLikeValueRoleName extends AbstractAttributeLikeValue {

    public AttributeLikeValueRoleName(String equalsValue) {
        super("roleName", equalsValue);
    }

}
