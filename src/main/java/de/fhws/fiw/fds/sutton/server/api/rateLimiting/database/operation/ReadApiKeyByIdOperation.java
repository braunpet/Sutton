package de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.operation;

import de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.models.ApiKeyDB;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.model.AbstractReadByIdOperation;
import jakarta.persistence.EntityManagerFactory;

public class ReadApiKeyByIdOperation extends AbstractReadByIdOperation<ApiKeyDB> {

    public ReadApiKeyByIdOperation(EntityManagerFactory emf, long idToLoad) {
        super(emf, ApiKeyDB.class, idToLoad);
    }

}
