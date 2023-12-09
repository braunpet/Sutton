package de.fhws.fiw.fds.sutton.server.api.security.database.operations.user_role;

import de.fhws.fiw.fds.sutton.server.api.security.database.models.RoleDB;
import de.fhws.fiw.fds.sutton.server.api.security.database.models.UserDB;
import de.fhws.fiw.fds.sutton.server.api.security.database.models.UserRoleDB;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.relation.AbstractPersistRelationByPrimaryIdOperation;
import jakarta.persistence.EntityManagerFactory;

public class PersistUserRoleOperation extends AbstractPersistRelationByPrimaryIdOperation<UserDB, RoleDB, UserRoleDB> {

    public PersistUserRoleOperation(EntityManagerFactory emf, long primaryId, RoleDB roleDB) {
        super(emf, UserRoleDB.class, UserDB.class, primaryId, roleDB);
    }

}
