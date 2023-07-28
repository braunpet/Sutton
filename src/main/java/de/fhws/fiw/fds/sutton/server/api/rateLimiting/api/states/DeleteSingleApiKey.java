package de.fhws.fiw.fds.sutton.server.api.rateLimiting.api.states;

import de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.dao.IRateLimiterDaoSupplier;
import de.fhws.fiw.fds.sutton.server.api.rateLimiting.models.ApiKey;
import de.fhws.fiw.fds.sutton.server.api.states.delete.AbstractDeleteState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;

import java.util.List;

import static de.fhws.fiw.fds.sutton.server.database.hibernate.DatabaseInstaller.RoleNames.ADMIN_ROLES;

public class DeleteSingleApiKey extends AbstractDeleteState<ApiKey>
        implements IRateLimiterDaoSupplier {

    private String apiKey;

    public DeleteSingleApiKey(DeleteApiKeyStateBuilder builder) {
        super(builder);
        this.apiKey = builder.apiKey;
    }

    @Override
    protected List<String> getAllowedRoles() {
        return ADMIN_ROLES;
    }

    @Override
    protected void authorizeRequest() {

    }

    @Override
    protected SingleModelResult<ApiKey> loadModel() {
        return getApiKeyDao().readApiKey(this.apiKey);
    }

    @Override
    protected NoContentResult deleteModel() {
        return getApiKeyDao().deleteApiKey(this.apiKey);
    }

    @Override
    protected void defineTransitionLinks() {

    }

    public static class DeleteApiKeyStateBuilder extends AbstractDeleteState.AbstractDeleteStateBuilder {

        private String apiKey;

        public DeleteApiKeyStateBuilder setApiKey(String apiKey){
            this.apiKey = apiKey;
            return this;
        }

        @Override
        public DeleteSingleApiKey build() {
            return new DeleteSingleApiKey(this);
        }
    }
}
