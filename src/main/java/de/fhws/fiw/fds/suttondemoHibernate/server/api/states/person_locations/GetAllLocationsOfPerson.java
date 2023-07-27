package de.fhws.fiw.fds.suttondemoHibernate.server.api.states.person_locations;

import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetCollectionRelationState;
import de.fhws.fiw.fds.suttondemoHibernate.server.api.models.Location;
import de.fhws.fiw.fds.suttondemoHibernate.server.api.queries.QueryByLocationName;

import javax.ws.rs.core.GenericEntity;
import java.util.Collection;
import java.util.List;

import static de.fhws.fiw.fds.sutton.server.database.hibernate.DatabaseInstaller.RoleNames.USER_ROLES;


public class GetAllLocationsOfPerson extends AbstractGetCollectionRelationState<Location> {
    public GetAllLocationsOfPerson(final Builder builder) {
        super(builder);
    }

    @Override
    protected void authorizeRequest() {
        QueryByLocationName theQuery = (QueryByLocationName) this.query;
        int waitingTime = theQuery.getWaitingTime();

        try {
            Thread.sleep(waitingTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void defineHttpResponseBody() {
        this.responseBuilder.entity(new GenericEntity<Collection<Location>>(this.result.getResult()) {
        });
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(PersonLocationUri.REL_PATH,
                PersonLocationRelTypes.CREATE_LOCATION,
                getAcceptRequestHeader(),
                this.primaryId);


        addLink(PersonLocationUri.REL_PATH_SHOW_ALL,
                PersonLocationRelTypes.GET_ALL_LOCATIONS,
                getAcceptRequestHeader(),
                this.primaryId);

    }

    @Override
    protected List<String> getAllowedRoles() {
        return USER_ROLES;
    }

    public static class Builder extends AbstractGetCollectionRelationStateBuilder<Location> {
        @Override
        public AbstractState build() {
            return new GetAllLocationsOfPerson(this);
        }
    }
}
