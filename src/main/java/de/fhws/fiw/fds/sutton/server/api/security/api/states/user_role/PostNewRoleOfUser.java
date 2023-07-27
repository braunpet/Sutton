package de.fhws.fiw.fds.sutton.server.api.security.api.states.user_role;

import de.fhws.fiw.fds.sutton.server.api.security.database.dao.IAuthDaoSupplier;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.post.AbstractPostRelationState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.api.security.models.Role;

import java.util.List;

import static de.fhws.fiw.fds.sutton.server.database.hibernate.DatabaseInstaller.RoleNames.ADMIN_ROLES;
import static de.fhws.fiw.fds.sutton.server.database.hibernate.DatabaseInstaller.RoleNames.MOD_ROLES;

public class PostNewRoleOfUser extends AbstractPostRelationState<Role>
        implements IAuthDaoSupplier {

    public PostNewRoleOfUser(final Builder builder) {
        super(builder);
    }

    @Override
    protected void authorizeRequest() {

    }

    @Override
    protected NoContentResult saveModel() {
        return getUserRoleDao().create(this.primaryId, this.modelToStore);
    }

    @Override
    protected void defineTransitionLinks() {

    }

    @Override
    protected List<String> getAllowedRoles() {
        return ADMIN_ROLES;
    }

    public static class Builder extends AbstractPostRelationStateBuilder<Role> {
        @Override
        public AbstractState build() {
            return new PostNewRoleOfUser(this);
        }
    }
}
