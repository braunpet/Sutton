package de.fhws.fiw.fds.suttondemoHibernate.server.api.states.locations;

import de.fhws.fiw.fds.sutton.server.api.queries.AbstractQuery;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetCollectionState;
import de.fhws.fiw.fds.sutton.server.database.DatabaseException;
import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.suttondemoHibernate.server.DaoFactory;
import de.fhws.fiw.fds.suttondemoHibernate.server.api.models.Location;

import javax.ws.rs.core.GenericEntity;
import java.util.Collection;

public class GetAllLocations extends AbstractGetCollectionState<Location> {
    public GetAllLocations(final Builder builder) {
        super(builder);
    }

    @Override
    protected void authorizeRequest() {

    }

    protected void defineHttpResponseBody() {
        this.responseBuilder.entity(new GenericEntity<Collection<Location>>(this.result.getResult()) {
        });
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(LocationUri.REL_PATH, LocationRelTypes.CREATE_LOCATION, getAcceptRequestHeader());
    }

    public static class AllLocations extends AbstractQuery<Location> {
        @Override
        protected CollectionModelResult<Location> doExecuteQuery(SearchParameter searchParameter) throws DatabaseException {
            return DaoFactory.getInstance().getLocationDao().readAll();
        }
    }

    public static class Builder extends AbstractGetCollectionStateBuilder<Location> {
        @Override
        public AbstractState build() {
            return new GetAllLocations(this);
        }
    }
}
