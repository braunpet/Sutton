package de.fhws.fiw.fds.sutton.server.api.security.api.states.user;

import de.fhws.fiw.fds.sutton.server.api.security.database.dao.IAuthDaoSupplier;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetCollectionState;
import de.fhws.fiw.fds.sutton.server.api.security.models.User;

import javax.ws.rs.core.GenericEntity;
import java.util.Collection;

public class GetAllUsers extends AbstractGetCollectionState<User>
        implements IAuthDaoSupplier {

    public GetAllUsers(final Builder builder) {
        super(builder);
    }

    protected void defineHttpResponseBody() {
        this.responseBuilder.entity(new GenericEntity<Collection<User>>(this.result.getResult()) {
        });
    }

    @Override
    protected void authorizeRequest() {

    }

    @Override
    protected void defineTransitionLinks() {
        addLink(UserUri.REL_PATH, UserRelTypes.CREATE_USER, getAcceptRequestHeader());
    }

    public static class Builder extends AbstractGetCollectionStateBuilder<User> {

        @Override
        public AbstractState build() {
            return new GetAllUsers(this);
        }
    }
}
