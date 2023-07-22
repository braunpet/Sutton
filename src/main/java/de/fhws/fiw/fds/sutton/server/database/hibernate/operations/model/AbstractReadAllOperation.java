package de.fhws.fiw.fds.sutton.server.database.hibernate.operations.model;

import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.hibernate.models.AbstractDBModel;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.AbstractDatabaseOperationWithSearchParameter;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.CollectionModelHibernateResult;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;

import java.util.List;

/**
 * This abstract class represents an operation to read all entities of type T from the database
 * based on the provided search parameters. It extends the AbstractDatabaseOperationWithSearchParameter class
 * to perform the database read operation.
 *
 * @param <T> The type of the entity extending AbstractDBModel to be read from the database.
 */
public abstract class AbstractReadAllOperation<T extends AbstractDBModel>
        extends AbstractDatabaseOperationWithSearchParameter<T> {

    /**
     * The class of the entity to be read.
     */
    private final Class<T> clazz;

    /**
     * Constructs a new AbstractReadAllOperation with the provided EntityManagerFactory,
     * class of the entity to be read, and search parameters.
     *
     * @param emf             The EntityManagerFactory used for database access.
     * @param clazz           The class of the entity to be read.
     * @param searchParameter The search parameters used for filtering and ordering the result set.
     */
    public AbstractReadAllOperation(EntityManagerFactory emf, Class<T> clazz, SearchParameter searchParameter) {
        super(emf, searchParameter);
        this.clazz = clazz;
    }

    /**
     * Runs the database operation to read all entities of type T from the database
     * based on the provided search parameters.
     *
     * @return A CollectionModelHibernateResult containing the list of entities read from the database.
     */
    @Override
    public CollectionModelHibernateResult<T> run() {
        var returnValue = new CollectionModelHibernateResult<>(readResult());
        returnValue.setTotalNumberOfResult(getTotalNumberOfResults());
        return returnValue;
    }

    /**
     * Reads the result from the database based on the search parameters.
     *
     * @return A list of entities of type T read from the database.
     */
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

    /**
     * Calculates the total number of entities of type T that match the search parameters.
     *
     * @return The total number of entities of type T that match the search parameters.
     */
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

