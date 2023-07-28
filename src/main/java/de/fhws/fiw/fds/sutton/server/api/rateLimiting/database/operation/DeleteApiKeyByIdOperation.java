package de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.operation;

import de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.models.ApiKeyDB;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.model.AbstractDeleteByIdOperation;
import jakarta.persistence.EntityManagerFactory;

public class DeleteApiKeyByIdOperation extends AbstractDeleteByIdOperation<ApiKeyDB> {

    public DeleteApiKeyByIdOperation(EntityManagerFactory emf, long idToDelete) {
        super(emf, ApiKeyDB.class, idToDelete);
    }

}
