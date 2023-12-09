package de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.operation;

import de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.models.ApiKeyDB;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.model.AbstractUpdateOperation;
import jakarta.persistence.EntityManagerFactory;

public class UpdateApiKeyOperation extends AbstractUpdateOperation<ApiKeyDB> {

    public UpdateApiKeyOperation(EntityManagerFactory emf, ApiKeyDB modelToUpdate) {
        super(emf, modelToUpdate);
    }

}
