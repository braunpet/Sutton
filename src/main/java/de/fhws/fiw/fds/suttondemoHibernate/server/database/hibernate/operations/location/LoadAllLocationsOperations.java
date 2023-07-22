package de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.operations.location;

import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.model.AbstractReadAllOperation;
import de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.models.LocationDB;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;

import java.util.List;

public class LoadAllLocationsOperations extends AbstractReadAllOperation<LocationDB> {

    public LoadAllLocationsOperations(EntityManagerFactory emf, SearchParameter searchParameter) {
        super(emf, LocationDB.class, searchParameter);
    }

    @Override
    public List<Predicate> getAdditionalPredicates(CriteriaBuilder cb, From from) {
        return null;
    }
}
