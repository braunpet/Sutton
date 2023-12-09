package de.fhws.fiw.fds.suttondemoHibernate.server.api.states.person_locations;

import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.post.AbstractPostRelationState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.suttondemoHibernate.server.DaoFactory;
import de.fhws.fiw.fds.suttondemoHibernate.server.api.models.Location;

import java.util.List;

import static de.fhws.fiw.fds.sutton.server.AbstractDatabaseInstaller.RoleNames.USER_ROLES;

public class PostNewLocationOfPerson extends AbstractPostRelationState<Location> {

    public PostNewLocationOfPerson( final Builder builder )
    {
        super( builder );
    }

    @Override
    protected void authorizeRequest() {

    }

    @Override protected NoContentResult saveModel( )
    {
        return DaoFactory.getInstance( ).getPersonLocationDao( ).create( this.primaryId, this.modelToStore );
    }

    @Override protected void defineTransitionLinks( )
    {

    }

    @Override
    protected List<String> getAllowedRoles() {
        return USER_ROLES;
    }

    public static class Builder extends AbstractPostRelationStateBuilder<Location>
    {
        @Override public AbstractState build( )
        {
            return new PostNewLocationOfPerson( this );
        }
    }

}
