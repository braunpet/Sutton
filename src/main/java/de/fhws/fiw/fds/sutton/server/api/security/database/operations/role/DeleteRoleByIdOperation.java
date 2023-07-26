package de.fhws.fiw.fds.sutton.server.api.security.database.operations.role;

import de.fhws.fiw.fds.sutton.server.api.security.database.models.RoleDB;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.model.AbstractDeleteByIdOperation;
import jakarta.persistence.EntityManagerFactory;

public class DeleteRoleByIdOperation extends AbstractDeleteByIdOperation<RoleDB> {

    public DeleteRoleByIdOperation(EntityManagerFactory emf, long idToDelete) {
        super(emf, RoleDB.class, idToDelete);
    }

}
