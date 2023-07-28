package de.fhws.fiw.fds.suttondemoHibernate.server.api.states.locations;

import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.put.AbstractPutState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.suttondemoHibernate.server.DaoFactory;
import de.fhws.fiw.fds.suttondemoHibernate.server.api.models.Location;

import java.util.List;

import static de.fhws.fiw.fds.sutton.server.AbstractDatabaseInstaller.RoleNames.USER_ROLES;

public class PutSingleLocation extends AbstractPutState<Location> {

    public PutSingleLocation( final Builder builder )
    {
        super( builder );
    }

    @Override
    protected void authorizeRequest() {

    }

    @Override protected SingleModelResult<Location> loadModel( )
    {
        return DaoFactory.getInstance( ).getLocationDao( ).readById( this.modelToUpdate.getId( ) );
    }

    @Override protected NoContentResult updateModel( )
    {
        return DaoFactory.getInstance( ).getLocationDao( ).update( this.modelToUpdate );
    }

    @Override protected void defineTransitionLinks( )
    {
        addLink( LocationUri.REL_PATH_ID, LocationRelTypes.GET_SINGLE_LOCATION, getAcceptRequestHeader( ),
                this.modelToUpdate.getId( ) );
    }

    @Override
    protected List<String> getAllowedRoles() {
        return USER_ROLES;
    }

    public static class Builder extends AbstractPutStateBuilder<Location>
    {
        @Override public AbstractState build( )
        {
            return new PutSingleLocation( this );
        }
    }

}
