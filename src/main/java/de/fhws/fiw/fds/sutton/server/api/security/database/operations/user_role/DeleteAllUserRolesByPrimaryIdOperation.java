package de.fhws.fiw.fds.sutton.server.api.security.database.operations.user_role;

import de.fhws.fiw.fds.sutton.server.api.security.database.models.RoleDB;
import de.fhws.fiw.fds.sutton.server.api.security.database.models.UserDB;
import de.fhws.fiw.fds.sutton.server.api.security.database.models.UserRoleDB;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.relation.AbstractDeleteAllRelationsByPrimaryIdOperation;
import jakarta.persistence.EntityManagerFactory;

public class DeleteAllUserRolesByPrimaryIdOperation extends AbstractDeleteAllRelationsByPrimaryIdOperation<UserDB, RoleDB, UserRoleDB> {

    public DeleteAllUserRolesByPrimaryIdOperation(EntityManagerFactory emf, long primaryId) {
        super(emf, UserRoleDB.class, primaryId);
    }

}
