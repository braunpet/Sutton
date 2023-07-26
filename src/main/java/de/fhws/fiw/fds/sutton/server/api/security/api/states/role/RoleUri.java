package de.fhws.fiw.fds.sutton.server.api.security.api.states.role;

import de.fhws.fiw.fds.sutton.server.AbstractStart;

public interface RoleUri {

    String PATH_ELEMENT = "roles";
    String REL_PATH = AbstractStart.getContextPath() + "/api/" + PATH_ELEMENT;
    String REL_PATH_ID = REL_PATH + "/{id}";

}
