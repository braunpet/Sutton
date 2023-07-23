package de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.api.states;

import de.fhws.fiw.fds.sutton.server.AbstractStart;

public interface BinaryDataUri {

    String PATH_ELEMENT = "binaryData";
    String REL_PATH = AbstractStart.getContextPath() + "/api/" + PATH_ELEMENT;
    String REL_PATH_ID = REL_PATH + "/{id}";

}
