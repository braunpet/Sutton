package de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate;

import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.CollectionModelHibernateResult;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.SingleModelHibernateResult;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.suttondemoHibernate.server.PersonLocationDao;
import de.fhws.fiw.fds.suttondemoHibernate.server.api.models.Location;
import de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.dao.*;
import de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.datafaker.LocationDataFaker;
import de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.datafaker.PersonDataFaker;
import de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.models.LocationDB;
import de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.models.PersonDB;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PersonLocationDaoAdapter implements PersonLocationDao {

    private PersonLocationDaoHibernate dao = new PersonLocationDaoHibernateImpl();
    private final PersonDataFaker personFaker = new PersonDataFaker();
    private final LocationDataFaker locationFaker = new LocationDataFaker();

    @Override
    public NoContentResult create(long primaryId, Location model) {
        LocationDB dbModel = createFrom(model);
        NoContentResult returnValue = this.dao.create(primaryId, dbModel);
        model.setId(dbModel.getId());
        return returnValue;
    }

    @Override
    public NoContentResult update(long primaryId, Location model) {
        LocationDB dbModel = createFrom(model);
        NoContentResult returnValue = this.dao.update(primaryId, dbModel);
        model.setId(dbModel.getId());
        return returnValue;
    }

    @Override
    public NoContentResult deleteRelation(long primaryId, long secondaryId) {
        return this.dao.deleteRelation(primaryId, secondaryId);
    }

    @Override
    public NoContentResult deleteRelationsFromPrimary(long primaryId) {
        return this.dao.deleteRelationsFromPrimary(primaryId);
    }

    @Override
    public NoContentResult deleteRelationsToSecondary(long secondaryId) {
        return this.dao.deleteRelationsToSecondary(secondaryId);
    }

    @Override
    public SingleModelResult<Location> readById(long primaryId, long secondaryId) {
        return new SingleModelResult<>(createFrom(this.dao.readById(primaryId, secondaryId).getResult()));
    }

    @Override
    public CollectionModelResult<Location> readAll(long primaryId, SearchParameter searchParameter) {
        return createResult(this.dao.readAll(primaryId, searchParameter));
    }

    @Override
    public CollectionModelResult<Location> readByCityName(long primaryId, String cityName, SearchParameter searchParameter) {
        return createResult(this.dao.readByCityName(primaryId, cityName, searchParameter));
    }

    @Override
    public void resetDatabase() {
        deleteAllDataAndRelations();
    }

    @Override
    public void initializeDatabase() {
        populateDatabase();
    }

    private Collection<Location> createFrom(Collection<LocationDB> models) {
        return models.stream().map(m -> createFrom(m)).collect(Collectors.toList());
    }

    private Location createFrom(LocationDB model) {
        final Location returnValue = new Location();
        returnValue.setId(model.getId());
        returnValue.setCityName(model.getCityName());
        returnValue.setLongitude(model.getLongitude());
        returnValue.setLatitude(model.getLatitude());
        returnValue.setVisitedOn(model.getVisitedOn());
        return returnValue;
    }

    private LocationDB createFrom(Location model) {
        final LocationDB returnValue = new LocationDB();
        returnValue.setId(model.getId());
        returnValue.setCityName(model.getCityName());
        returnValue.setLongitude(model.getLongitude());
        returnValue.setLatitude(model.getLatitude());
        returnValue.setVisitedOn(model.getVisitedOn());
        return returnValue;
    }

    private SingleModelResult<Location> createResult(SingleModelHibernateResult<LocationDB> result) {
        if (result.hasError()) {
            final SingleModelResult<Location> returnValue = new SingleModelResult<>();
            returnValue.setError(result.getErrorCode(), result.getErrorMessage());
            return returnValue;
        } else {
            return new SingleModelResult<>(createFrom(result.getResult()));
        }
    }

    private CollectionModelResult<Location> createResult(CollectionModelHibernateResult<LocationDB> result) {
        if (result.hasError()) {
            final CollectionModelResult<Location> returnValue = new CollectionModelResult<>();
            returnValue.setError(result.getErrorCode(), result.getErrorMessage());
            return returnValue;
        } else {
            final CollectionModelResult returnValue = new CollectionModelResult<>(createFrom(result.getResult()));
            returnValue.setTotalNumberOfResult(result.getTotalNumberOfResult());
            return returnValue;
        }
    }

    private void populateDatabase() {
        IntStream.range(0, 50).forEach(i -> {
            PersonDaoHibernate personDaoHibernate = new PersonDaoHibernateImpl();
            PersonDB personDB = createPerson();
            personDaoHibernate.create(personDB);
            IntStream.range(0, 25).forEach(j -> this.dao.create(personDB.getId(), createLocation()));
        });
    }

    private PersonDB createPerson() {
        return this.personFaker.createPerson();
    }

    private LocationDB createLocation() {
        return this.locationFaker.createLocation();
    }


    private void deleteAllDataAndRelations() {
        PersonDaoHibernate personDaoHibernate = new PersonDaoHibernateImpl();
        LocationDaoHibernate locationDaoHibernate = new LocationDaoHibernateImpl();

        personDaoHibernate.readAll().getResult().forEach(p -> {
            this.dao.readAll(p.getId(), SearchParameter.DEFAULT).getResult()
                    .forEach(l -> {
                        this.dao.deleteRelation(p.getId(), l.getId());
                    });

            personDaoHibernate.delete(p.getId());
        });

        locationDaoHibernate.readAll().getResult().forEach(l -> locationDaoHibernate.delete(l.getId()));
    }
}
