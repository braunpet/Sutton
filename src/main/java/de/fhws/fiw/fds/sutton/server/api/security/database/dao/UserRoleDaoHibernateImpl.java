package de.fhws.fiw.fds.sutton.server.api.security.database.dao;

import de.fhws.fiw.fds.sutton.server.api.security.database.models.RoleDB;
import de.fhws.fiw.fds.sutton.server.api.security.database.operations.user_role.*;
import de.fhws.fiw.fds.sutton.server.database.hibernate.IDatabaseConnection;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.CollectionModelHibernateResult;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.SingleModelHibernateResult;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;
import jakarta.persistence.EntityManagerFactory;

public class UserRoleDaoHibernateImpl implements UserRoleDaoHibernate{

    private static final EntityManagerFactory emf = IDatabaseConnection.SUTTON_EMF;

    @Override
    public NoContentResult create(long primaryId, RoleDB secondaryModel) {
        return new PersistUserRoleOperation(emf, primaryId, secondaryModel).start();
    }

    @Override
    public NoContentResult update(long primaryId, RoleDB secondaryModel) {
        return new UpdateUserRoleOperation(emf, primaryId, secondaryModel).start();
    }

    @Override
    public NoContentResult deleteRelation(long primaryId, long secondaryId) {
        return new DeleteUserRoleByIdOperation(emf, primaryId, secondaryId).start();
    }

    @Override
    public NoContentResult deleteRelationsFromPrimary(long primaryId) {
        return new DeleteAllUserRolesByPrimaryIdOperation(emf, primaryId).start();
    }

    @Override
    public NoContentResult deleteRelationsToSecondary(long secondaryId) {
        return new DeleteAllUserRolesBySecondaryIdOperation(emf, secondaryId).start();
    }

    @Override
    public SingleModelHibernateResult<RoleDB> readById(long primaryId, long secondaryId) {
        return new ReadUserRoleByIdOperation(emf, primaryId, secondaryId).start();
    }

    @Override
    public CollectionModelHibernateResult<RoleDB> readAll(long primaryId, SearchParameter searchParameter) {
        return new ReadAllUserRolesOperation(emf, primaryId, searchParameter).start();
    }

    @Override
    public CollectionModelHibernateResult<RoleDB> readRolesByUserName(String userName) {
        return new ReadRolesByUserNameOperation(emf, userName).start();
    }
}
