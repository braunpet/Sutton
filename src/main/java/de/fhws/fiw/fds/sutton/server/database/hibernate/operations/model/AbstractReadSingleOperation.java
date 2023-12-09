package de.fhws.fiw.fds.sutton.server.database.hibernate.operations.model;

import de.fhws.fiw.fds.sutton.server.database.hibernate.models.AbstractDBModel;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.AbstractDatabaseOperationWithSearchParameter;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.SingleModelHibernateResult;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import javax.ws.rs.core.Response;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

/**
 * This abstract class represents an operation to read a single entity of type T from the database
 * based on the provided search parameters. It extends the AbstractDatabaseOperationWithSearchParameter class
 * to perform the database read operation.
 *
 * @param <T> The type of the entity extending AbstractDBModel to be read from the database.
 */
public abstract class AbstractReadSingleOperation<T extends AbstractDBModel>
        extends AbstractDatabaseOperationWithSearchParameter<T, SingleModelHibernateResult> {

    /**
     * The class of the entity to be read.
     */
    private final Class<T> clazz;

    /**
     * Constructs a new AbstractReadSingleByPredicateOperation with the provided EntityManagerFactory,
     * class of the entity to be read, and search parameters.
     *
     * @param emf             The EntityManagerFactory used for database access.
     * @param clazz           The class of the entity to be read.
     */
    public AbstractReadSingleOperation(EntityManagerFactory emf, Class<T> clazz) {
        super(emf, SingleModelHibernateResult.class, null);
        this.clazz = clazz;
    }

    /**
     * Runs the database operation to read a single entity of type T from the database
     * based on the {@link AbstractDatabaseOperationWithSearchParameter#getAdditionalPredicates}.
     *
     * @return A SingleModelHibernateResult containing the entity read from the database.
     * @throws NoSuchMethodException if the specified method is not found.
     * @throws InvocationTargetException if an error occurs during method invocation.
     * @throws InstantiationException if an object cannot be instantiated.
     * @throws IllegalAccessException if access to the method or field is denied.
     */
    @Override
    protected SingleModelHibernateResult<T> run() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<T> cq = cb.createQuery(this.clazz);
        final Root<T> rootEntry = cq.from(this.clazz);

        final CriteriaQuery<T> all = cq.select(rootEntry)
                .where(getAdditionalPredicates(cb, rootEntry).toArray(new Predicate[0]));
        final TypedQuery<T> allQuery = em.createQuery(all);

        Optional<T> result = allQuery
                .setHint("org.hibernate.cacheable", true)
                .getResultList().stream().findFirst();

        return result.map(SingleModelHibernateResult::new)
                .orElseGet(() -> new SingleModelHibernateResult.SingleModelHibernateResultBuilder<T>()
                        .setError(Response.Status.NOT_FOUND.getStatusCode(), "Requested entity not found in Database.")
                        .build());
    }

}

