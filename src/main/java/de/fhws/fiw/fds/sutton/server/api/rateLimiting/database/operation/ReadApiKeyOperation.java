package de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.operation;

import de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.models.ApiKeyDB;
import de.fhws.fiw.fds.sutton.server.database.hibernate.models.SuttonColumnConstants;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.AbstractDatabaseOperation;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.SingleModelHibernateResult;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.lang.reflect.InvocationTargetException;

public class ReadApiKeyOperation extends AbstractDatabaseOperation<ApiKeyDB, SingleModelHibernateResult<ApiKeyDB>> {

    private final String apiKey;

    public ReadApiKeyOperation(EntityManagerFactory emf, String apiKey) {
        super(emf);
        this.apiKey = apiKey;
    }

    @Override
    protected SingleModelHibernateResult<ApiKeyDB> run() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ApiKeyDB> find = cb.createQuery(ApiKeyDB.class);
        Root<ApiKeyDB> rootEntry = find.from(ApiKeyDB.class);

        Predicate apiKeyEquals = cb.equal(rootEntry.get(SuttonColumnConstants.API_KEY), this.apiKey);
        find.where(apiKeyEquals);
        TypedQuery<ApiKeyDB> findQuery = em.createQuery(find);

        return new SingleModelHibernateResult<>(findQuery.getResultStream().findFirst().orElse(null));
    }

    @Override
    protected SingleModelHibernateResult<ApiKeyDB> errorResult() {
        final SingleModelHibernateResult<ApiKeyDB> returnValue = new SingleModelHibernateResult<>();
        returnValue.setError();
        return returnValue;
    }
}
