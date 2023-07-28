package de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.operation;

import de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.models.ApiKeyDB;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.model.AbstractPersistOperation;
import jakarta.persistence.EntityManagerFactory;

public class PersistApiKeyOperation extends AbstractPersistOperation<ApiKeyDB> {

    public PersistApiKeyOperation(EntityManagerFactory emf, ApiKeyDB modelToPersist) {
        super(emf, modelToPersist);
    }

}
