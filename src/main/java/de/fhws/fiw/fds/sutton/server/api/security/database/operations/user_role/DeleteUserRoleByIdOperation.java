package de.fhws.fiw.fds.sutton.server.api.security.database.operations.user_role;

import de.fhws.fiw.fds.sutton.server.api.security.database.models.RoleDB;
import de.fhws.fiw.fds.sutton.server.api.security.database.models.UserDB;
import de.fhws.fiw.fds.sutton.server.api.security.database.models.UserRoleDB;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.relation.AbstractDeleteRelationByIdOperation;
import jakarta.persistence.EntityManagerFactory;

public class DeleteUserRoleByIdOperation extends AbstractDeleteRelationByIdOperation<UserDB, RoleDB, UserRoleDB> {

    public DeleteUserRoleByIdOperation(EntityManagerFactory emf, long primaryId, long secondaryId) {
        super(emf, UserRoleDB.class, primaryId, secondaryId);
    }

}
