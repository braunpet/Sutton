package de.fhws.fiw.fds.sutton.server.api.security.database.operations.role;

import de.fhws.fiw.fds.sutton.server.api.security.database.models.RoleDB;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.model.AbstractReadByIdOperation;
import jakarta.persistence.EntityManagerFactory;

public class ReadRoleByIdOperation extends AbstractReadByIdOperation<RoleDB> {

    public ReadRoleByIdOperation(EntityManagerFactory emf, long idToLoad) {
        super(emf, RoleDB.class, idToLoad);
    }

}
