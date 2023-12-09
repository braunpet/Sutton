package de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.operations.personLocation;

import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.relation.AbstractReadAllRelationsByPrimaryIdOperation;
import de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.models.LocationDB;
import de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.models.PersonDB;
import de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.models.PersonLocationDB;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;

import java.util.List;


public class LoadAllPersonLocationsOperation extends AbstractReadAllRelationsByPrimaryIdOperation<PersonDB, LocationDB, PersonLocationDB> {

    public LoadAllPersonLocationsOperation(EntityManagerFactory emf, long primaryId, SearchParameter searchParameter) {
        super(emf, PersonLocationDB.class, primaryId, searchParameter);
    }

    @Override
    public List<Predicate> getAdditionalPredicates(CriteriaBuilder cb, From from) {
        return null;
    }
}
