package de.fhws.fiw.fds.sutton.server.api.rateLimiting.api.states;

import de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.dao.IRateLimiterDaoSupplier;
import de.fhws.fiw.fds.sutton.server.api.rateLimiting.models.ApiKey;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetState;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;

import java.util.List;

import static de.fhws.fiw.fds.sutton.server.database.hibernate.DatabaseInstaller.RoleNames.MOD_ROLES;

public class GetSingleApiKey extends AbstractGetState<ApiKey>
        implements IRateLimiterDaoSupplier {

    private String apiKey;

    public GetSingleApiKey(GetApiKeyStateBuilder builder) {
        super(builder);
        this.apiKey = builder.apiKey;
    }

    @Override
    protected List<String> getAllowedRoles() {
        return MOD_ROLES;
    }

    @Override
    protected void authorizeRequest() {

    }

    @Override
    protected SingleModelResult<ApiKey> loadModel() {
        return getApiKeyDao().readApiKey(this.apiKey);
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(ApiKeyUri.REL_PATH_API_KEY, ApiKeyRelTypes.DELETE_SINGLE_APIKEY, getAcceptRequestHeader(),
                this.requestedId);
    }

    public static class GetApiKeyStateBuilder extends AbstractGetStateBuilder {

        private String apiKey;

        public GetApiKeyStateBuilder setApiKey(String apiKey){
            this.apiKey = apiKey;
            return this;
        }

        @Override
        public GetSingleApiKey build() {
            return new GetSingleApiKey(this);
        }
    }
}
