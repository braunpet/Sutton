package de.fhws.fiw.fds.sutton.server.api.security.database.operations.role;

import de.fhws.fiw.fds.sutton.server.api.security.database.models.RoleDB;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.model.AbstractUpdateOperation;
import jakarta.persistence.EntityManagerFactory;

public class UpdateRoleOperation extends AbstractUpdateOperation<RoleDB> {

    public UpdateRoleOperation(EntityManagerFactory emf, RoleDB modelToUpdate) {
        super(emf, modelToUpdate);
    }

}
