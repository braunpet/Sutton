package de.fhws.fiw.fds.sutton.server.api.security.api.states.user_role;

import de.fhws.fiw.fds.sutton.server.AbstractStart;

public interface UserRoleUri {

    String SHOW_ALL_PARAMETER = "showAll";
    String PATH_ELEMENT = "users/{id}/roles";
    String REL_PATH = AbstractStart.getContextPath() + "/api/" + PATH_ELEMENT;
    String REL_PATH_SHOW_ALL = AbstractStart.getContextPath() + "/api/" + PATH_ELEMENT + "?" + SHOW_ALL_PARAMETER + "=true";
    String REL_PATH_SHOW_ONLY_LINKED = AbstractStart.getContextPath() + "/api/" + PATH_ELEMENT + "?" + SHOW_ALL_PARAMETER + "=false";
    String REL_PATH_ID = REL_PATH + "/{id}";

}
