package de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.dao;

import de.fhws.fiw.fds.sutton.server.api.rateLimiting.models.ApiKey;
import de.fhws.fiw.fds.sutton.server.database.IDatabaseAccessObject;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;

public interface ApiKeyDao extends IDatabaseAccessObject<ApiKey> {

    SingleModelResult<ApiKey> generateApiKey(long resetRate, long requestLimit);

    SingleModelResult<ApiKey> readApiKey(String apiKey);

    NoContentResult deleteApiKey(String apiKey);

}
