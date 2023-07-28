package de.fhws.fiw.fds.sutton.server.api.rateLimiting.api.states;

import de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.dao.IRateLimiterDaoSupplier;
import de.fhws.fiw.fds.sutton.server.api.rateLimiting.models.ApiKey;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.post.AbstractPostState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;

import java.util.List;

import static de.fhws.fiw.fds.sutton.server.database.hibernate.DatabaseInstaller.RoleNames.MOD_ROLES;

public class PostApiKey extends AbstractPostState<ApiKey>
        implements IRateLimiterDaoSupplier {

    public PostApiKey(final Builder builder) {
        super(builder);
    }

    @Override
    protected NoContentResult saveModel() {
        return getApiKeyDao().create(this.modelToStore);
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

    public static class Builder extends AbstractPostStateBuilder<ApiKey> {
        @Override
        public AbstractState build() {
            return new PostApiKey(this);
        }
    }
}
