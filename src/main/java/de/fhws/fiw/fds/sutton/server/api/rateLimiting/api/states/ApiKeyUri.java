package de.fhws.fiw.fds.sutton.server.api.rateLimiting.api.states;

import de.fhws.fiw.fds.sutton.server.AbstractStart;

public interface ApiKeyUri {

    String PATH_ELEMENT = "apikeys";
    String REL_PATH = AbstractStart.getContextPath() + "/api/" + PATH_ELEMENT;
    String REL_PATH_ID = REL_PATH + "/{id}";
    String REL_PATH_API_KEY = REL_PATH + "/{apiKey}";

}
