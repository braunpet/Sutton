package de.fhws.fiw.fds.sutton.server.api.rateLimiting.api.states;

import de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.dao.IRateLimiterDaoSupplier;
import de.fhws.fiw.fds.sutton.server.api.rateLimiting.models.ApiKey;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetState;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;

import java.util.List;

import static de.fhws.fiw.fds.sutton.server.database.hibernate.DatabaseInstaller.RoleNames.MOD_ROLES;

public class GetSingleApiKeyById extends AbstractGetState<ApiKey>
        implements IRateLimiterDaoSupplier {

    public GetSingleApiKeyById(AbstractGetStateBuilder builder) {
        super(builder);
    }

    @Override
    protected SingleModelResult<ApiKey> loadModel() {
        return getApiKeyDao().readById(requestedId);
    }

    @Override
    protected void authorizeRequest() {
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(ApiKeyUri.REL_PATH_ID, ApiKeyRelTypes.UPDATE_SINGLE_APIKEY, getAcceptRequestHeader(),
                this.requestedId);
        addLink(ApiKeyUri.REL_PATH_ID, ApiKeyRelTypes.DELETE_SINGLE_APIKEY, getAcceptRequestHeader(),
                this.requestedId);
    }

    @Override
    protected List<String> getAllowedRoles() {
        return MOD_ROLES;
    }

    public static class GetApiKeyByIdStateBuilder extends AbstractGetStateBuilder {
        @Override
        public GetSingleApiKeyById build() {
            return new GetSingleApiKeyById(this);
        }
    }
}
