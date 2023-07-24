package de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.database.operations;

import de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.database.models.BinaryDataDBModel;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.model.AbstractReadAllOperation;
import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;

import java.util.List;
import java.util.ArrayList;

public class LoadAllBinaryDataByMediaTypeOperation extends AbstractReadAllOperation<BinaryDataDBModel> {

    private String mediaType;

    public LoadAllBinaryDataByMediaTypeOperation(EntityManagerFactory emf, String mediaType) {
        super(emf, BinaryDataDBModel.class, SearchParameter.DEFAULT);
        this.mediaType = mediaType;
    }

    @Override
    public List<Predicate> getAdditionalPredicates(CriteriaBuilder cb, From from) {
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(from.get("mediaType"), mediaType));
        return predicates;
    }
}
