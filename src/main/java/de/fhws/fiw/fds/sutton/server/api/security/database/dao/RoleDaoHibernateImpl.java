package de.fhws.fiw.fds.sutton.server.api.security.database.dao;

import de.fhws.fiw.fds.sutton.server.api.security.database.models.RoleDB;
import de.fhws.fiw.fds.sutton.server.api.security.database.operations.role.*;
import de.fhws.fiw.fds.sutton.server.database.hibernate.IDatabaseConnection;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.CollectionModelHibernateResult;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.SingleModelHibernateResult;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;
import jakarta.persistence.EntityManagerFactory;

public class RoleDaoHibernateImpl implements RoleDaoHibernate{

    private static final EntityManagerFactory emf = IDatabaseConnection.SUTTON_EMF;

    @Override
    public NoContentResult create(RoleDB model) {
        return new PersistRoleOperation(emf, model).start();
    }

    @Override
    public SingleModelHibernateResult<RoleDB> readById(long id) {
        return new ReadRoleByIdOperation(emf, id).start();
    }

    @Override
    public CollectionModelHibernateResult<RoleDB> readAll(SearchParameter searchParameter) {
        return new RealAllRolesOperation(emf, searchParameter).start();
    }

    @Override
    public NoContentResult update(RoleDB model) {
        return new UpdateRoleOperation(emf, model).start();
    }

    @Override
    public NoContentResult delete(long id) {
        return new DeleteRoleByIdOperation(emf, id).start();
    }

}
