package de.fhws.fiw.fds.sutton.server.api.rateLimiting.api.states;

import de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.dao.IRateLimiterDaoSupplier;
import de.fhws.fiw.fds.sutton.server.api.rateLimiting.models.ApiKey;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.delete.AbstractDeleteState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;

import java.util.List;

import static de.fhws.fiw.fds.sutton.server.database.hibernate.DatabaseInstaller.RoleNames.ADMIN_ROLES;

public class DeleteSingleApiKeyById extends AbstractDeleteState<ApiKey>
        implements IRateLimiterDaoSupplier {

    public DeleteSingleApiKeyById(final Builder builder) {
        super(builder);
    }

    @Override
    protected SingleModelResult<ApiKey> loadModel() {
        return getApiKeyDao().readById(this.modelIdToDelete);
    }

    @Override
    protected NoContentResult deleteModel() {
        return getApiKeyDao().delete(this.modelIdToDelete);
    }

    @Override
    protected void defineTransitionLinks() {
    }

    @Override
    protected void authorizeRequest() {

    }

    @Override
    protected List<String> getAllowedRoles() {
        return ADMIN_ROLES;
    }

    public static class Builder extends AbstractDeleteStateBuilder {
        @Override
        public AbstractState build() {
            return new DeleteSingleApiKeyById(this);
        }
    }
}
