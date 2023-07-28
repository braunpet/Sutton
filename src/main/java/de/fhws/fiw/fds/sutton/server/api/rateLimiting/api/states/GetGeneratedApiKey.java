package de.fhws.fiw.fds.sutton.server.api.rateLimiting.api.states;

import de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.dao.IRateLimiterDaoSupplier;
import de.fhws.fiw.fds.sutton.server.api.rateLimiting.models.ApiKey;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetState;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;

import java.util.List;

import static de.fhws.fiw.fds.sutton.server.database.hibernate.DatabaseInstaller.RoleNames.MOD_ROLES;

public class GetGeneratedApiKey extends AbstractGetState<ApiKey>
        implements IRateLimiterDaoSupplier {

    private long resetRate;
    private long requestLimit;

    public GetGeneratedApiKey(Builder builder) {
        super(builder);
        this.resetRate = builder.resetRate;
        this.requestLimit = builder.requestLimit;
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
        return getApiKeyDao().generateApiKey(this.resetRate, this.requestLimit);
    }

    @Override
    protected void defineTransitionLinks() {

    }

    public static class Builder extends AbstractGetStateBuilder {

        private long resetRate;
        private long requestLimit;

        public Builder setResetRate(long resetRate) {
            this.resetRate = resetRate;
            return this;
        }

        public Builder setRequestLimit(long requestLimit) {
            this.requestLimit = requestLimit;
            return this;
        }

        @Override
        public GetGeneratedApiKey build() {
            return new GetGeneratedApiKey(this);
        }
    }
}
