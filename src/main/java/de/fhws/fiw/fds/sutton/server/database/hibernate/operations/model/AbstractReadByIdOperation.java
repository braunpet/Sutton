package de.fhws.fiw.fds.sutton.server.database.hibernate.operations.model;

import de.fhws.fiw.fds.sutton.server.database.hibernate.models.AbstractDBModel;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.AbstractDatabaseOperation;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.SingleModelHibernateResult;
import jakarta.persistence.EntityManagerFactory;

import javax.ws.rs.core.Response;

/**
 * This abstract class represents an operation to read a database entity by its ID.
 * It extends the AbstractDatabaseOperation class to perform the database read operation.
 *
 * @param <T> The type of the entity extending AbstractDBModel to be read.
 */
public abstract class AbstractReadByIdOperation<T extends AbstractDBModel>
        extends AbstractDatabaseOperation<T, SingleModelHibernateResult> {

    /**
     * The ID of the entity to be loaded.
     */
    private long idToLoad;

    /**
     * The class of the entity to be loaded.
     */
    private Class<T> clazz;

    /**
     * Constructs a new AbstractReadByIdOperation with the provided EntityManagerFactory, class of the entity, and ID to load.
     *
     * @param emf      The EntityManagerFactory used for database access.
     * @param type     The class of the entity to be loaded.
     * @param idToLoad The ID of the entity to be loaded.
     */
    public AbstractReadByIdOperation(EntityManagerFactory emf, Class<T> type, long idToLoad) {
        super(emf);
        this.clazz = type;
        this.idToLoad = idToLoad;
    }

    /**
     * Runs the database operation to read the entity by its ID.
     * It uses the EntityManager's find method to retrieve the entity from the database.
     *
     * @return A SingleModelHibernateResult containing the loaded entity if found, or {@link AbstractReadByIdOperation#errorResult()} if not
     */
    @Override
    protected SingleModelHibernateResult<T> run() {
        final T result = this.em.find(this.clazz, this.idToLoad);

        if (result != null) {
            return new SingleModelHibernateResult<>(result);
        } else {
            return errorResult();
        }
    }

    /**
     * Provides the error result in case of a failed read operation.
     *
     * @return A SingleModelHibernateResult indicating a failed read operation with an error.
     */
    @Override
    protected SingleModelHibernateResult<T> errorResult() {
        return new SingleModelHibernateResult.SingleModelHibernateResultBuilder<T>()
                .setError(Response.Status.NOT_FOUND.getStatusCode(), "Requested entity not found in Database.")
                .build();
    }
}

