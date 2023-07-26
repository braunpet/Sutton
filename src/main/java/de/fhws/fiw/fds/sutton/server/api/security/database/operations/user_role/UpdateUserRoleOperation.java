package de.fhws.fiw.fds.sutton.server.api.security.database.operations.user_role;

import de.fhws.fiw.fds.sutton.server.api.security.database.models.RoleDB;
import de.fhws.fiw.fds.sutton.server.api.security.database.models.UserDB;
import de.fhws.fiw.fds.sutton.server.api.security.database.models.UserRoleDB;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.relation.AbstractUpdateRelationByPrimaryIdOperation;
import jakarta.persistence.EntityManagerFactory;

public class UpdateUserRoleOperation extends AbstractUpdateRelationByPrimaryIdOperation<UserDB, RoleDB, UserRoleDB> {

    public UpdateUserRoleOperation(EntityManagerFactory emf, long primaryId, RoleDB secondaryModelToUpdate) {
        super(emf, UserRoleDB.class, UserDB.class, primaryId, secondaryModelToUpdate);
    }

}
