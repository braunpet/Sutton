package de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.dao;

public interface IRateLimiterDaoSupplier {

    default ApiKeyDao getApiKeyDao() {
        return new ApiKeyDaoAdapter();
    }

}
