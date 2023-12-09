package de.fhws.fiw.fds.sutton.server.api.security.database.operations.user;

import de.fhws.fiw.fds.sutton.server.api.security.database.models.UserDB;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.model.AbstractPersistOperation;
import jakarta.persistence.EntityManagerFactory;

public class PersistUserOperation extends AbstractPersistOperation<UserDB> {

    public PersistUserOperation(EntityManagerFactory emf, UserDB modelToPersist) {
        super(emf, modelToPersist);
    }

}
