package de.fhws.fiw.fds.sutton.server.api.rateLimiting.api.states;

import de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.dao.IRateLimiterDaoSupplier;
import de.fhws.fiw.fds.sutton.server.api.rateLimiting.models.ApiKey;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.put.AbstractPutState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;

import java.util.List;

import static de.fhws.fiw.fds.sutton.server.database.hibernate.DatabaseInstaller.RoleNames.ADMIN_ROLES;

public class PutApiKey extends AbstractPutState<ApiKey>
        implements IRateLimiterDaoSupplier {

    public PutApiKey(final Builder builder) {
        super(builder);
    }

    @Override
    protected SingleModelResult<ApiKey> loadModel() {
        return getApiKeyDao().readById(this.modelToUpdate.getId());
    }

    @Override
    protected NoContentResult updateModel() {
        return getApiKeyDao().update(this.modelToUpdate);
    }

    @Override
    protected void authorizeRequest() {
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(ApiKeyUri.REL_PATH_ID, ApiKeyRelTypes.GET_SINGLE_APIKEY, getAcceptRequestHeader(),
                this.modelToUpdate.getId());
        addLink(ApiKeyUri.REL_PATH, ApiKeyRelTypes.CREATE_APIKEY, getAcceptRequestHeader());
    }

    @Override
    protected List<String> getAllowedRoles() {
        return ADMIN_ROLES;
    }

    public static class Builder extends AbstractPutStateBuilder<ApiKey> {
        @Override
        public AbstractState build() {
            return new PutApiKey(this);
        }
    }
}
