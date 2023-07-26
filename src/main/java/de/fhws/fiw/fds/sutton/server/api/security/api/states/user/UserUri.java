package de.fhws.fiw.fds.sutton.server.api.security.api.states.user;

import de.fhws.fiw.fds.sutton.server.AbstractStart;

public interface UserUri {

    String PATH_ELEMENT = "users";
    String REL_PATH = AbstractStart.getContextPath() + "/api/" + PATH_ELEMENT;
    String REL_PATH_ID = REL_PATH + "/{id}";

}
