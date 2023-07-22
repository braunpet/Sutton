package de.fhws.fiw.fds.sutton.server.api.rateLimiting.operation;

import de.fhws.fiw.fds.sutton.server.api.rateLimiting.model.APIKey;
import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.model.AbstractReadAllOperation;
import jakarta.persistence.EntityManagerFactory;

public class ReadAllAPIKeysOperation extends AbstractReadAllOperation<APIKey> {

    public ReadAllAPIKeysOperation(EntityManagerFactory emf, SearchParameter searchParameter) {
        super(emf, APIKey.class, searchParameter);
    }

}
