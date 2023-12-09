package de.fhws.fiw.fds.sutton.server.api.security.database.operations.user;

import de.fhws.fiw.fds.sutton.server.api.security.database.models.UserDB;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.model.AbstractDeleteByIdOperation;
import jakarta.persistence.EntityManagerFactory;

public class DeleteUserByIdOperation extends AbstractDeleteByIdOperation<UserDB> {

    public DeleteUserByIdOperation(EntityManagerFactory emf, long idToDelete) {
        super(emf, UserDB.class, idToDelete);
    }

}
