package de.fhws.fiw.fds.suttondemoHibernate.server.api.states.locations;

import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.delete.AbstractDeleteState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.suttondemoHibernate.server.DaoFactory;
import de.fhws.fiw.fds.suttondemoHibernate.server.api.models.Location;
import de.fhws.fiw.fds.suttondemoHibernate.server.api.states.persons.PersonRelTypes;
import de.fhws.fiw.fds.suttondemoHibernate.server.api.states.persons.PersonUri;

import java.util.List;

import static de.fhws.fiw.fds.sutton.server.database.hibernate.DatabaseInstaller.RoleNames.MOD_ROLES;
import static de.fhws.fiw.fds.sutton.server.database.hibernate.DatabaseInstaller.RoleNames.USER_ROLES;

public class DeleteSingleLocation extends AbstractDeleteState<Location> {

    public DeleteSingleLocation( final Builder builder )
    {
        super( builder );
    }

    @Override
    protected void authorizeRequest() {

    }

    @Override protected SingleModelResult<Location> loadModel( )
    {
        return DaoFactory.getInstance( ).getLocationDao( ).readById( this.modelIdToDelete );
    }

    @Override protected NoContentResult deleteModel( )
    {
        DaoFactory.getInstance( ).getPersonLocationDao( ).deleteRelationsToSecondary( this.modelIdToDelete );
        return DaoFactory.getInstance( ).getPersonDao( ).delete( this.modelIdToDelete );
    }

    @Override protected void defineTransitionLinks( )
    {
        addLink( PersonUri.REL_PATH, PersonRelTypes.GET_ALL_PERSONS, getAcceptRequestHeader( ) );
    }

    @Override
    protected List<String> getAllowedRoles() {
        return USER_ROLES;
    }

    public static class Builder extends AbstractDeleteStateBuilder
    {
        @Override public AbstractState build( )
        {
            return new DeleteSingleLocation( this );
        }
    }

}
