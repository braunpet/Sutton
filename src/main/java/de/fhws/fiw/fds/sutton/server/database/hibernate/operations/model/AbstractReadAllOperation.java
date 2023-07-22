package de.fhws.fiw.fds.sutton.server.database.hibernate.operations.model;

import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.hibernate.models.AbstractDBModel;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.AbstractDatabaseOrderByOperation;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.CollectionModelHibernateResult;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;

public abstract class AbstractReadAllOperation<T extends AbstractDBModel>
        extends AbstractDatabaseOrderByOperation<T> {

    private final Class<T> clazz;
    private final SearchParameter searchParameter;

    public AbstractReadAllOperation(EntityManagerFactory emf, Class<T> clazz, SearchParameter searchParameter) {
        super(emf);
        this.clazz = clazz;
        this.searchParameter = searchParameter;
    }

    @Override
    protected CollectionModelHibernateResult<T> run() {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<T> cq = cb.createQuery(this.clazz);
        final Root<T> rootEntry = cq.from(this.clazz);

        Predicate[] predicatesFromSearchParameter = getPredicatesFromSearchParameter(cb, rootEntry, this.searchParameter);
        final CriteriaQuery<T> all = cq.select(rootEntry)
                .where(predicatesFromSearchParameter)
                .orderBy(getOrderFromSearchParameter(cb, rootEntry, this.searchParameter));
        final TypedQuery<T> allQuery = em.createQuery(all);

        final List<T> result = allQuery
                .setHint("org.hibernate.cacheable", true)
                .setFirstResult(this.searchParameter.getOffset())
                .setMaxResults(this.searchParameter.getSize())
                .getResultList();

        return new CollectionModelHibernateResult<>(result);
    }

}
