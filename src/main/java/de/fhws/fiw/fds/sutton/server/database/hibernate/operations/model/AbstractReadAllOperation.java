package de.fhws.fiw.fds.sutton.server.database.hibernate.operations.model;

import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.hibernate.models.AbstractDBModel;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.AbstractDatabaseOperationWithSearchParameter;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.CollectionModelHibernateResult;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;

import java.util.List;

public abstract class AbstractReadAllOperation<T extends AbstractDBModel>
        extends AbstractDatabaseOperationWithSearchParameter<T> {

    private final Class<T> clazz;

    public AbstractReadAllOperation(EntityManagerFactory emf, Class<T> clazz, SearchParameter searchParameter) {
        super(emf, searchParameter);
        this.clazz = clazz;
    }

    @Override
    public CollectionModelHibernateResult<T> run() {
        var returnValue = new CollectionModelHibernateResult<>(readResult());
        returnValue.setTotalNumberOfResult(getTotalNumberOfResults());
        return returnValue;
    }

    private List<T> readResult() {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<T> cq = cb.createQuery(this.clazz);
        final Root<T> rootEntry = cq.from(this.clazz);


        final CriteriaQuery<T> all = cq.select(rootEntry)
                .where(getPredicates(cb, rootEntry))
                .orderBy(getOrderFromSearchParameter(cb, rootEntry));
        final TypedQuery<T> allQuery = em.createQuery(all);

        return allQuery
                .setHint("org.hibernate.cacheable", true)
                .setFirstResult(this.searchParameter.getOffset())
                .setMaxResults(this.searchParameter.getSize())
                .getResultList();
    }

    private int getTotalNumberOfResults() {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        final Root<T> rootEntry = cq.from(this.clazz);

        cq.select(cb.count(rootEntry)).where(getPredicates(cb, rootEntry));

        return this.em.createQuery(cq)
                .setHint("org.hibernate.cacheable", true)
                .getSingleResult().intValue();
    }

}
