package de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.dao;

import de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.models.ApiKeyDB;
import de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.operation.*;
import de.fhws.fiw.fds.sutton.server.IDatabaseConnection;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.CollectionModelHibernateResult;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.SingleModelHibernateResult;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;
import jakarta.persistence.EntityManagerFactory;

public class ApiKeyDaoHibernateImpl implements ApiKeyDaoHibernate{

    private static final EntityManagerFactory emf = IDatabaseConnection.SUTTON_EMF;

    @Override
    public SingleModelHibernateResult<ApiKeyDB> readApiKey(String apiKey) {
        return new ReadApiKeyOperation(emf, apiKey).start();
    }

    @Override
    public NoContentResult deleteApiKey(String apiKey) {
        ApiKeyDB apiKeyDB = readApiKey(apiKey).getResult();
        return delete(apiKeyDB.getId());
    }

    @Override
    public NoContentResult create(ApiKeyDB model) {
        return new PersistApiKeyOperation(emf, model).start();
    }

    @Override
    public SingleModelHibernateResult<ApiKeyDB> readById(long id) {
        return new ReadApiKeyByIdOperation(emf, id).start();
    }

    @Override
    public CollectionModelHibernateResult<ApiKeyDB> readAll(SearchParameter searchParameter) {
        return new ReadAllApiKeysOperation(emf, searchParameter).start();
    }

    @Override
    public NoContentResult update(ApiKeyDB model) {
        return new UpdateApiKeyOperation(emf, model).start();
    }

    @Override
    public NoContentResult delete(long id) {
        return new DeleteApiKeyByIdOperation(emf, id).start();
    }
}
