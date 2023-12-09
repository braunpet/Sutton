package de.fhws.fiw.fds.sutton.server.api.security.api.states.user_role;

import de.fhws.fiw.fds.sutton.server.api.security.database.dao.IAuthDaoSupplier;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.delete.AbstractDeleteRelationState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.api.security.models.Role;

import java.util.List;

import static de.fhws.fiw.fds.sutton.server.AbstractDatabaseInstaller.RoleNames.ADMIN_ROLES;

public class DeleteSingleRoleOfUser extends AbstractDeleteRelationState<Role>
        implements IAuthDaoSupplier {

    public DeleteSingleRoleOfUser(final Builder builder) {
        super(builder);
    }

    @Override
    protected void authorizeRequest() {

    }

    @Override
    protected SingleModelResult<Role> loadModel() {
        return getUserRoleDao().readById(this.primaryId, this.modelIdToDelete);
    }

    @Override
    protected NoContentResult deleteModel() {
        return getUserRoleDao().deleteRelation(this.primaryId, this.modelIdToDelete);
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(UserRoleUri.REL_PATH,
                UserRoleRelTypes.GET_ALL_LINKED_ROLES,
                getAcceptRequestHeader(),
                this.primaryId);
    }

    @Override
    protected List<String> getAllowedRoles() {
        return ADMIN_ROLES;
    }

    public static class Builder extends AbstractDeleteRelationStateBuilder {
        @Override
        public AbstractState build() {
            return new DeleteSingleRoleOfUser(this);
        }
    }

}
