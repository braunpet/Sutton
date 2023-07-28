package de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.operation;

import de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.models.ApiKeyDB;
import de.fhws.fiw.fds.sutton.server.database.hibernate.models.SuttonColumnConstants;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.model.AbstractReadSingleOperation;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;

import java.util.List;

public class ReadApiKeyOperation extends AbstractReadSingleOperation<ApiKeyDB> {

    private final String apiKey;

    public ReadApiKeyOperation(EntityManagerFactory emf, String apiKey) {
        super(emf, ApiKeyDB.class);
        this.apiKey = apiKey;
    }

    @Override
    public List<Predicate> getAdditionalPredicates(CriteriaBuilder cb, From from) {
        return List.of(cb.equal(from.get(SuttonColumnConstants.API_KEY), this.apiKey));
    }
}
