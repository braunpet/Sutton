package de.fhws.fiw.fds.sutton.server.api.security.api.states.role;

import de.fhws.fiw.fds.sutton.server.api.security.database.dao.IAuthDaoSupplier;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.post.AbstractPostState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.api.security.models.Role;

import java.util.List;

import static de.fhws.fiw.fds.sutton.server.database.hibernate.DatabaseInstaller.RoleNames.ADMIN_ROLES;

public class PostRole extends AbstractPostState<Role>
        implements IAuthDaoSupplier {

    public PostRole(final Builder builder) {
        super(builder);
    }

    @Override
    protected NoContentResult saveModel() {
        return getRoleDao().create(this.modelToStore);
    }

    @Override
    protected void authorizeRequest() {
    }

    @Override
    protected void defineTransitionLinks() {
    }

    @Override
    protected List<String> getAllowedRoles() {
        return ADMIN_ROLES;
    }

    public static class Builder extends AbstractPostStateBuilder<Role> {
        @Override
        public AbstractState build() {
            return new PostRole(this);
        }
    }
}
