package de.fhws.fiw.fds.sutton.server.api.security.api.states.user;

import de.fhws.fiw.fds.sutton.server.api.security.database.dao.IAuthDaoSupplier;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.put.AbstractPutState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.api.security.models.User;

import java.util.List;

import static de.fhws.fiw.fds.sutton.server.AbstractDatabaseInstaller.RoleNames.ADMIN_ROLES;

public class PutUser extends AbstractPutState<User>
        implements IAuthDaoSupplier {

    public PutUser(final Builder builder) {
        super(builder);
    }

    @Override
    protected SingleModelResult<User> loadModel() {
        return getUserDao().readById(this.modelToUpdate.getId());
    }

    @Override
    protected NoContentResult updateModel() {
        return getUserDao().update(this.modelToUpdate);
    }

    @Override
    protected void authorizeRequest() {
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(UserUri.REL_PATH_ID, UserRelTypes.GET_SINGLE_USER, getAcceptRequestHeader(),
                this.modelToUpdate.getId());
        addLink(UserUri.REL_PATH, UserRelTypes.CREATE_USER, getAcceptRequestHeader());
    }

    @Override
    protected List<String> getAllowedRoles() {
        return ADMIN_ROLES;
    }

    public static class Builder extends AbstractPutStateBuilder<User> {
        @Override
        public AbstractState build() {
            return new PutUser(this);
        }
    }
}
