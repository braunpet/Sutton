package de.fhws.fiw.fds.sutton.server.api.security.database.operations.user;

import de.fhws.fiw.fds.sutton.server.api.security.database.models.UserDB;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.model.AbstractReadByIdOperation;
import jakarta.persistence.EntityManagerFactory;

public class ReadUserByIdOperation extends AbstractReadByIdOperation<UserDB> {

    public ReadUserByIdOperation(EntityManagerFactory emf, long idToLoad) {
        super(emf, UserDB.class, idToLoad);
    }

}
