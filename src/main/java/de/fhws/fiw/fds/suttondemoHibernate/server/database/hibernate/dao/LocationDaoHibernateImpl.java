package de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.dao;

import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.hibernate.IDatabaseConnection;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.CollectionModelHibernateResult;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.SingleModelHibernateResult;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.models.LocationDB;
import de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.operations.location.*;
import jakarta.persistence.EntityManagerFactory;

public class LocationDaoHibernateImpl implements LocationDaoHibernate {

    private static final EntityManagerFactory emf = IDatabaseConnection.SUTTON_EMF;

    public LocationDaoHibernateImpl() {
        super();
    }

    @Override
    public NoContentResult create(LocationDB model) {
        return new PersistLocationOperation(emf, model).start();
    }

    @Override
    public SingleModelHibernateResult<LocationDB> readById(long id) {
        return new LoadLocationByIdOperation(emf, id).start();
    }

    @Override
    public CollectionModelHibernateResult<LocationDB> readAll(SearchParameter searchParameter) {
        return new LoadAllLocationsOperations(emf, searchParameter).start();
    }

    @Override
    public NoContentResult update(LocationDB model) {
        return new UpdateLocationOperation(emf, model).start();
    }

    @Override
    public NoContentResult delete(long id) {
        return new DeleteOperationLocationById(emf, id).start();
    }
}
