package de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.dao;

import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;
import de.fhws.fiw.fds.sutton.server.IDatabaseConnection;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.CollectionModelHibernateResult;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.SingleModelHibernateResult;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.models.LocationDB;
import de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.operations.personLocation.*;
import jakarta.persistence.EntityManagerFactory;


public class PersonLocationDaoHibernateImpl implements PersonLocationDaoHibernate {

    private static final EntityManagerFactory emf = IDatabaseConnection.SUTTON_EMF;

    public PersonLocationDaoHibernateImpl() {
        super();
    }

    @Override
    public NoContentResult create(long primaryId, LocationDB secondaryModel) {
        return new PersistPersonLocationOperation(emf, primaryId, secondaryModel).start();
    }

    @Override
    public NoContentResult update(long primaryId, LocationDB secondaryModel) {
        return new UpdatePersonLocationOperation(emf, primaryId, secondaryModel).start();
    }

    @Override
    public NoContentResult deleteRelation(long primaryId, long secondaryId) {
        return new DeletePersonLocationOperation(emf, primaryId, secondaryId).start();
    }

    @Override
    public NoContentResult deleteRelationsFromPrimary(long primaryId) {
        return new DeleteAllPersonLocationsByPrimaryIdOperation(emf, primaryId).start();
    }

    @Override
    public NoContentResult deleteRelationsToSecondary(long secondaryId) {
        return new DeleteAllPersonLocationsBySecondaryIdOperation(emf, secondaryId).start();
    }

    @Override
    public SingleModelHibernateResult<LocationDB> readById(long primaryId, long secondaryId) {
        return new LoadPersonLocationOperation(emf, primaryId, secondaryId).start();
    }

    @Override
    public CollectionModelHibernateResult<LocationDB> readAll(long primaryId, SearchParameter searchParameter) {
        return new LoadAllPersonLocationsOperation(emf, primaryId, searchParameter).start();
    }

    @Override
    public CollectionModelHibernateResult<LocationDB> readByCityName(long primaryId, String cityName, SearchParameter searchParameter) {
        return new LoadPersonLocationByCityName(emf, primaryId, cityName, searchParameter).start();
    }
}
