package de.fhws.fiw.fds.sutton.server.api.security.api.states.user;

import de.fhws.fiw.fds.sutton.server.api.security.database.dao.IAuthDaoSupplier;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetState;
import de.fhws.fiw.fds.sutton.server.api.security.models.User;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;

public class GetSingleUser extends AbstractGetState<User>
        implements IAuthDaoSupplier {

    public GetSingleUser(AbstractGetStateBuilder builder) {
        super(builder);
    }

    @Override
    protected SingleModelResult<User> loadModel() {
        return getUserDao().readById(requestedId);
    }

    @Override
    protected void authorizeRequest() {
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(UserUri.REL_PATH_ID, UserRelTypes.UPDATE_SINGLE_USER, getAcceptRequestHeader(),
                this.requestedId);
        addLink(UserUri.REL_PATH_ID, UserRelTypes.DELETE_SINGLE_USER, getAcceptRequestHeader(),
                this.requestedId);
    }

    public static class GetUserStateBuilder extends AbstractGetStateBuilder {
        @Override
        public GetSingleUser build() {
            return new GetSingleUser(this);
        }
    }
}
