package de.fhws.fiw.fds.sutton.server.api.rateLimiting.api.states;

import de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.dao.IRateLimiterDaoSupplier;
import de.fhws.fiw.fds.sutton.server.api.rateLimiting.models.ApiKey;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetCollectionState;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;

import javax.ws.rs.core.GenericEntity;
import java.util.Collection;
import java.util.List;

import static de.fhws.fiw.fds.sutton.server.AbstractDatabaseInstaller.RoleNames.MOD_ROLES;

public class GetAllApiKeys extends AbstractGetCollectionState<ApiKey>
        implements IRateLimiterDaoSupplier {

    public GetAllApiKeys(final Builder builder) {
        super(builder);
    }

    protected void defineHttpResponseBody() {
        this.responseBuilder.entity(new GenericEntity<Collection<ApiKey>>(this.result.getResult()) {
        });
    }

    @Override
    protected void authorizeRequest() {

    }

    @Override
    protected CollectionModelResult<ApiKey> loadModels(){
        return getApiKeyDao().readAll();
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(ApiKeyUri.REL_PATH, ApiKeyRelTypes.CREATE_APIKEY, getAcceptRequestHeader());
    }

    @Override
    protected List<String> getAllowedRoles() {
        return MOD_ROLES;
    }

    public static class Builder extends AbstractGetCollectionStateBuilder<ApiKey> {

        @Override
        public AbstractState build() {
            return new GetAllApiKeys(this);
        }
    }
}
