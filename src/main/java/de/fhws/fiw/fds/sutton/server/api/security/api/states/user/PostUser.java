package de.fhws.fiw.fds.sutton.server.api.security.api.states.user;

import de.fhws.fiw.fds.sutton.server.api.security.database.dao.IAuthDaoSupplier;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.post.AbstractPostState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.api.security.models.User;

import java.util.List;

import static de.fhws.fiw.fds.sutton.server.AbstractDatabaseInstaller.RoleNames.MOD_ROLES;

public class PostUser extends AbstractPostState<User>
        implements IAuthDaoSupplier {

    public PostUser(final Builder builder) {
        super(builder);
    }

    @Override
    protected NoContentResult saveModel() {
        return getUserDao().create(this.modelToStore);
    }

    @Override
    protected void authorizeRequest() {
    }

    @Override
    protected void defineTransitionLinks() {
    }

    @Override
    protected List<String> getAllowedRoles() {
        return MOD_ROLES;
    }

    public static class Builder extends AbstractPostStateBuilder<User> {
        @Override
        public AbstractState build() {
            return new PostUser(this);
        }
    }
}
