package de.fhws.fiw.fds.sutton.server.api.security.database.operations.user_role;

import de.fhws.fiw.fds.sutton.server.api.security.database.models.RoleDB;
import de.fhws.fiw.fds.sutton.server.api.security.database.models.UserDB;
import de.fhws.fiw.fds.sutton.server.api.security.database.models.UserRoleDB;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.relation.AbstractDeleteAllRelationsBySecondaryIdOperation;
import jakarta.persistence.EntityManagerFactory;

public class DeleteAllUserRolesBySecondaryIdOperation extends AbstractDeleteAllRelationsBySecondaryIdOperation<UserDB, RoleDB, UserRoleDB> {

    public DeleteAllUserRolesBySecondaryIdOperation(EntityManagerFactory emf, long secondaryId) {
        super(emf, UserRoleDB.class, secondaryId);
    }

}
