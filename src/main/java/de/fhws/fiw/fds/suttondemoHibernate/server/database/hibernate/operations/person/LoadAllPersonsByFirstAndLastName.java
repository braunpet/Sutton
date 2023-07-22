package de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.operations.person;

import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.model.AbstractReadAllOperation;
import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;
import de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.models.PersonDB;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;

import java.util.List;

public class LoadAllPersonsByFirstAndLastName extends AbstractReadAllOperation<PersonDB> {

    private final String firstName;
    private final String lastName;

    public LoadAllPersonsByFirstAndLastName(EntityManagerFactory emf,
                                            String firstName,
                                            String lastName,
                                            SearchParameter searchParameter) {
        super(emf, PersonDB.class, searchParameter);
        this.firstName = firstName.toLowerCase();
        this.lastName = lastName.toLowerCase();
    }

    @Override
    public List<Predicate> getAdditionalPredicates(CriteriaBuilder cb, From from) {
        final Predicate matchFirstName = cb.like(cb.lower(from.get("firstName")), this.firstName + "%");
        final Predicate matchLastName = cb.like(cb.lower(from.get("lastName")), this.lastName + "%");
        return List.of(matchFirstName, matchLastName);
    }
}
