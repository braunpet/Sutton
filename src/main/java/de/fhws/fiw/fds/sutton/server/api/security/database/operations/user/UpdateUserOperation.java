package de.fhws.fiw.fds.sutton.server.api.security.database.operations.user;

import de.fhws.fiw.fds.sutton.server.api.security.database.models.UserDB;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.model.AbstractUpdateOperation;
import jakarta.persistence.EntityManagerFactory;

public class UpdateUserOperation extends AbstractUpdateOperation<UserDB> {

    public UpdateUserOperation(EntityManagerFactory emf, UserDB modelToUpdate) {
        super(emf, modelToUpdate);
    }

}
