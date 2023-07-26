package de.fhws.fiw.fds.sutton.server.api.security.database.operations.role;

import de.fhws.fiw.fds.sutton.server.api.security.database.models.RoleDB;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.model.AbstractPersistOperation;
import jakarta.persistence.EntityManagerFactory;

public class PersistRoleOperation extends AbstractPersistOperation<RoleDB> {

    public PersistRoleOperation(EntityManagerFactory emf, RoleDB modelToPersist) {
        super(emf, modelToPersist);
    }

}
