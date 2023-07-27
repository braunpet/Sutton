package de.fhws.fiw.fds.sutton.server.api.security.api.states.user_role;

import de.fhws.fiw.fds.sutton.server.api.security.database.dao.IAuthDaoSupplier;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.put.AbstractPutRelationState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.api.security.models.Role;

import java.util.List;

import static de.fhws.fiw.fds.sutton.server.database.hibernate.DatabaseInstaller.RoleNames.ADMIN_ROLES;
import static de.fhws.fiw.fds.sutton.server.database.hibernate.DatabaseInstaller.RoleNames.MOD_ROLES;

public class PutSingleRoleOfUser extends AbstractPutRelationState<Role>
        implements IAuthDaoSupplier {

    public PutSingleRoleOfUser(final Builder builder) {
        super(builder);
    }

    @Override
    protected void authorizeRequest() {

    }

    @Override
    protected SingleModelResult<Role> loadModel() {
        return getRoleDao().readById(this.requestedId);
    }

    @Override
    protected NoContentResult updateModel() {
        return getUserRoleDao().update(this.primaryId, this.modelToUpdate);
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(UserRoleUri.REL_PATH_ID,
                UserRoleRelTypes.GET_SINGLE_ROLE,
                getAcceptRequestHeader(),
                this.primaryId, this.requestedId);
    }

    @Override
    protected List<String> getAllowedRoles() {
        return ADMIN_ROLES;
    }

    public static class Builder extends AbstractPutRelationStateBuilder<Role> {
        @Override
        public AbstractState build() {
            return new PutSingleRoleOfUser(this);
        }
    }
}
