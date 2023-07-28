package de.fhws.fiw.fds.suttondemoHibernate.server.api.states.person_locations;

import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetRelationState;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.suttondemoHibernate.server.DaoFactory;
import de.fhws.fiw.fds.suttondemoHibernate.server.api.models.Location;

import java.util.List;

import static de.fhws.fiw.fds.sutton.server.AbstractDatabaseInstaller.RoleNames.USER_ROLES;

public class GetSingleLocationOfPerson extends AbstractGetRelationState<Location> {

    public GetSingleLocationOfPerson( final Builder builder )
    {
        super( builder );
    }

    @Override
    protected void authorizeRequest() {

    }

    @Override protected SingleModelResult<Location> loadModel( )
    {
        return DaoFactory.getInstance( ).getLocationDao( ).readById( this.requestedId );
    }

    @Override protected void defineTransitionLinks( )
    {
        addLink( PersonLocationUri.REL_PATH_SHOW_ONLY_LINKED,
                PersonLocationRelTypes.GET_ALL_LINKED_LOCATIONS,
                getAcceptRequestHeader( ),
                this.primaryId );

        if ( isPersonLinkedToThisLocation( ) )
        {
            addLink( PersonLocationUri.REL_PATH_ID,
                    PersonLocationRelTypes.UPDATE_SINGLE_LOCATION,
                    getAcceptRequestHeader( ),
                    this.primaryId, this.requestedId );

            addLink( PersonLocationUri.REL_PATH_ID,
                    PersonLocationRelTypes.DELETE_LINK_FROM_PERSON_TO_LOCATION,
                    getAcceptRequestHeader( ),
                    this.primaryId, this.requestedId );
        }
        else
        {
            addLink( PersonLocationUri.REL_PATH_ID,
                    PersonLocationRelTypes.CREATE_LINK_FROM_PERSON_TO_LOCATION,
                    getAcceptRequestHeader( ),
                    this.primaryId, this.requestedId );
        }
    }

    private boolean isPersonLinkedToThisLocation( )
    {
        return !DaoFactory.getInstance( )
                .getPersonLocationDao( )
                .readById( this.primaryId, this.requestedId )
                .isEmpty( );
    }

    @Override
    protected List<String> getAllowedRoles() {
        return USER_ROLES;
    }

    public static class Builder extends AbstractGetRelationStateBuilder
    {
        @Override public AbstractState build( )
        {
            return new GetSingleLocationOfPerson( this );
        }
    }

}
