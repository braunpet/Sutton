package de.fhws.fiw.fds.suttondemoHibernate.server.api.states.person_locations;

import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.put.AbstractPutRelationState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.suttondemoHibernate.server.DaoFactory;
import de.fhws.fiw.fds.suttondemoHibernate.server.api.models.Location;

import java.util.List;

import static de.fhws.fiw.fds.sutton.server.AbstractDatabaseInstaller.RoleNames.USER_ROLES;

public class PutSingleLocationOfPerson extends AbstractPutRelationState<Location> {

    public PutSingleLocationOfPerson(final Builder builder) {
        super(builder);
    }

    @Override
    protected void authorizeRequest() {

    }

    @Override
    protected SingleModelResult<Location> loadModel() {
        return DaoFactory.getInstance().getLocationDao().readById(this.requestedId);
    }

    @Override
    protected NoContentResult updateModel() {
        return DaoFactory.getInstance().getPersonLocationDao().update(this.primaryId, this.modelToUpdate);
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(PersonLocationUri.REL_PATH_ID,
                PersonLocationRelTypes.GET_SINGLE_LOCATION,
                getAcceptRequestHeader(),
                this.primaryId, this.requestedId);
    }

    @Override
    protected List<String> getAllowedRoles() {
        return USER_ROLES;
    }

    public static class Builder extends AbstractPutRelationStateBuilder<Location> {
        @Override
        public AbstractState build() {
            return new PutSingleLocationOfPerson(this);
        }
    }

}
