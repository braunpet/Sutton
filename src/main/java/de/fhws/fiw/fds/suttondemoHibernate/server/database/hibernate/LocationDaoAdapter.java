package de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate;

import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.CollectionModelHibernateResult;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.SingleModelHibernateResult;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.suttondemoHibernate.server.LocationDao;
import de.fhws.fiw.fds.suttondemoHibernate.server.api.models.Location;
import de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.dao.LocationDaoHibernate;
import de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.dao.LocationDaoHibernateImpl;
import de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.datafaker.LocationDataFaker;
import de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.models.LocationDB;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LocationDaoAdapter implements LocationDao {

    private LocationDaoHibernate dao = new LocationDaoHibernateImpl();
    private final LocationDataFaker faker = new LocationDataFaker();

    @Override
    public NoContentResult create(Location model) {
        final LocationDB dbModel = createFrom(model);
        final NoContentResult returnValue = this.dao.create(dbModel);
        model.setId(dbModel.getId());
        return returnValue;
    }

    @Override
    public SingleModelResult<Location> readById(long id) {
        return createResult(this.dao.readById(id));
    }

    @Override
    public CollectionModelResult<Location> readAll() {
        return this.readAll(new SearchParameter());
    }

    @Override
    public CollectionModelResult<Location> readAll(SearchParameter searchParameter) {
        return createResult(this.dao.readAll(searchParameter));
    }

    @Override
    public NoContentResult update(Location model) {
        return this.dao.update(createFrom(model));
    }

    @Override
    public NoContentResult delete(long id) {
        return this.dao.delete(id);
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
        if (result.isEmpty()) {
            return new SingleModelResult<>();
        }
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
        IntStream.range(0, 2).forEach(i -> this.dao.create(createLocation()));
    }

    private LocationDB createLocation() {
        return this.faker.createLocation();
    }

}
