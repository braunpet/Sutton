package de.fhws.fiw.fds.sutton.server.api.security.database.dao;

import de.fhws.fiw.fds.sutton.server.api.security.database.models.UserDB;
import de.fhws.fiw.fds.sutton.server.api.security.database.operations.user.*;
import de.fhws.fiw.fds.sutton.server.IDatabaseConnection;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.CollectionModelHibernateResult;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.SingleModelHibernateResult;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;
import jakarta.persistence.EntityManagerFactory;

public class UserDaoHibernateImpl implements UserDaoHibernate{

    private static final EntityManagerFactory emf = IDatabaseConnection.SUTTON_EMF;

    @Override
    public NoContentResult create(UserDB model) {
        return new PersistUserOperation(emf, model).start();
    }

    @Override
    public SingleModelHibernateResult<UserDB> readById(long id) {
        return new ReadUserByIdOperation(emf, id).start();
    }

    @Override
    public CollectionModelHibernateResult<UserDB> readAll(SearchParameter searchParameter) {
        return new ReadAllUsersOperation(emf, searchParameter).start();
    }

    @Override
    public NoContentResult update(UserDB model) {
        return new UpdateUserOperation(emf, model).start();
    }

    @Override
    public NoContentResult delete(long id) {
        return new DeleteUserByIdOperation(emf, id).start();
    }

    @Override
    public SingleModelHibernateResult<UserDB> readUserByName(String userName) {
        return new ReadUserByNameOperation(emf, userName).start();
    }
}
