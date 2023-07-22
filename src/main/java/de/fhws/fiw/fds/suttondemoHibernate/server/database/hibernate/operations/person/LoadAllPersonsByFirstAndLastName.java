package de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.operations.person;

import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.AbstractDatabaseOrderByOperation;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.CollectionModelHibernateResult;
import de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.models.PersonDB;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class LoadAllPersonsByFirstAndLastName extends AbstractDatabaseOrderByOperation<PersonDB> {

    private final String firstName;
    private final String lastName;

    private final SearchParameter searchParameter;

    public LoadAllPersonsByFirstAndLastName(EntityManagerFactory emf, String firstName, String lastName,
                                            SearchParameter searchParameter) {
        super(emf);
        this.firstName = firstName.toLowerCase();
        this.lastName = lastName.toLowerCase();
        this.searchParameter = searchParameter;
    }

    @Override
    protected CollectionModelHibernateResult<PersonDB> run() {
        var returnValue = new CollectionModelHibernateResult<>(readResult());
        returnValue.setTotalNumberOfResult(getTotalNumberOfResults());
        return returnValue;
    }

    private List<PersonDB> readResult() {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<PersonDB> cq = cb.createQuery(PersonDB.class);
        final Root<PersonDB> root = cq.from(PersonDB.class);
        final Predicate predicate = createPredicate(cb, root);

        cq.select(root).where(predicate).orderBy(getOrderFromSearchParameter(cb, root, this.searchParameter));

        return this.em.createQuery(cq)
                .setHint("org.hibernate.cacheable", true)
                .setFirstResult(this.searchParameter.getOffset())
                .setMaxResults(this.searchParameter.getSize())
                .getResultList();
    }

    private int getTotalNumberOfResults() {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        final Root<PersonDB> root = cq.from(PersonDB.class);
        final Predicate predicate = createPredicate(cb, root);

        cq.select(cb.count(root)).where(predicate);

        return this.em.createQuery(cq)
                .setHint("org.hibernate.cacheable", true)
                .getSingleResult().intValue();
    }

    private Predicate createPredicate(CriteriaBuilder cb, Root<PersonDB> root) {
        final Predicate matchFirstName = cb.like(cb.lower(root.get("firstName")), this.firstName.toLowerCase() + "%");
        final Predicate matchLastName = cb.like(cb.lower(root.get("lastName")), this.lastName.toLowerCase() + "%");
        return cb.and(matchFirstName, matchLastName);
    }

}
