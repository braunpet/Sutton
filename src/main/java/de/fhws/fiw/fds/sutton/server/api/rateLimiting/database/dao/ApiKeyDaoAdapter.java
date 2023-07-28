package de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.dao;

import de.fhws.fiw.fds.sutton.server.api.rateLimiting.ApiKeyGenerator;
import de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.models.ApiKeyDB;
import de.fhws.fiw.fds.sutton.server.api.rateLimiting.models.ApiKey;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.CollectionModelHibernateResult;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.SingleModelHibernateResult;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;

import java.util.Collection;
import java.util.stream.Collectors;

public class ApiKeyDaoAdapter implements ApiKeyDao{

    private ApiKeyDaoHibernate dao = new ApiKeyDaoHibernateImpl();

    @Override
    public SingleModelResult<ApiKey> generateApiKey(long resetRate, long requestLimit) {
        final ApiKey apiKey = ApiKeyGenerator.generateUniqueKey(resetRate, requestLimit);
        final ApiKeyDB dbModel = createFrom(apiKey);
        this.dao.create(dbModel);
        return createResult(this.dao.readApiKey(apiKey.getApiKey()));
    }

    @Override
    public SingleModelResult<ApiKey> readApiKey(String apiKey) {
        return createResult(dao.readApiKey(apiKey));
    }

    @Override
    public NoContentResult deleteApiKey(String apiKey) {
        return dao.deleteApiKey(apiKey);
    }

    @Override
    public NoContentResult create(ApiKey model) {
        final ApiKeyDB dbModel = createFrom(model);
        final NoContentResult returnValue = this.dao.create(dbModel);
        model.setId(dbModel.getId());
        return returnValue;
    }

    @Override
    public SingleModelResult<ApiKey> readById(long id) {
        return createResult(this.dao.readById(id));
    }

    @Override
    public CollectionModelResult<ApiKey> readAll(SearchParameter searchParameter) {
        return createResult(this.dao.readAll(searchParameter));
    }

    @Override
    public NoContentResult update(ApiKey model) {
        return this.dao.update(createFrom(model));
    }

    @Override
    public NoContentResult delete(long id) {
        return this.dao.delete(id);
    }

    private Collection<ApiKey> createFrom(Collection<ApiKeyDB> models) {
        return models.stream().map(m -> createFrom(m)).collect(Collectors.toList());
    }

    private ApiKey createFrom(ApiKeyDB model) {
        if(model == null){
            return null;
        }
        final ApiKey returnValue = new ApiKey();
        returnValue.setId(model.getId());
        returnValue.setApiKey(model.getApiKey());
        returnValue.setRequests(model.getRequests());
        returnValue.setLastReset(model.getLastReset());
        returnValue.setResetRateInSeconds(model.getResetRateInSeconds());
        returnValue.setRequestLimit(model.getRequestLimit());
        return returnValue;
    }

    private ApiKeyDB createFrom(ApiKey model) {
        final ApiKeyDB returnValue = new ApiKeyDB();
        returnValue.setId(model.getId());
        returnValue.setApiKey(model.getApiKey());
        returnValue.setRequests(model.getRequests());
        returnValue.setLastReset(model.getLastReset());
        returnValue.setResetRateInSeconds(model.getResetRateInSeconds());
        returnValue.setRequestLimit(model.getRequestLimit());
        return returnValue;
    }

    private SingleModelResult<ApiKey> createResult(SingleModelHibernateResult<ApiKeyDB> result) {
        if (result.hasError()) {
            final SingleModelResult<ApiKey> returnValue = new SingleModelResult<>();
            returnValue.setError(result.getErrorCode(), result.getErrorMessage());
            return returnValue;
        } else {
            return new SingleModelResult<>(createFrom(result.getResult()));
        }
    }

    private CollectionModelResult<ApiKey> createResult(CollectionModelHibernateResult<ApiKeyDB> result) {
        if (result.hasError()) {
            final CollectionModelResult<ApiKey> returnValue = new CollectionModelResult<>();
            returnValue.setError(result.getErrorCode(), result.getErrorMessage());
            return returnValue;
        } else {
            final CollectionModelResult<ApiKey> returnValue = new CollectionModelResult<>(createFrom(result.getResult()));
            returnValue.setTotalNumberOfResult(result.getTotalNumberOfResult());
            return returnValue;
        }
    }
}
