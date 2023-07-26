package de.fhws.fiw.fds.sutton.server.database.hibernate.operations.model;

import de.fhws.fiw.fds.sutton.server.database.hibernate.models.AbstractDBModel;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.AbstractDatabaseOperation;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import jakarta.persistence.EntityManagerFactory;

/**
 * This abstract class represents an operation to persist a database entity.
 * It extends the AbstractDatabaseOperation class to perform the database persistence operation.
 *
 * @param <T> The type of the entity extending AbstractDBModel to be persisted.
 */
public abstract class AbstractPersistOperation<T extends AbstractDBModel>
        extends AbstractDatabaseOperation<T, NoContentResult> {

    /**
     * The database entity to be persisted.
     */
    private T modelToPersist;

    /**
     * Constructs a new AbstractPersistOperation with the provided EntityManagerFactory and the database entity to persist.
     *
     * @param emf            The EntityManagerFactory used for database access.
     * @param modelToPersist The database entity to be persisted.
     */
    public AbstractPersistOperation(EntityManagerFactory emf, T modelToPersist) {
        super(emf);
        this.modelToPersist = modelToPersist;
    }

    /**
     * Runs the database operation to persist the entity.
     * It uses the EntityManager's persist method to save the entity in the database.
     *
     * @return A NoContentResult indicating successful persistence with no content to return.
     */
    @Override
    protected NoContentResult run() {
        this.em.persist(this.modelToPersist);
        return new NoContentResult();
    }

    /**
     * Provides the error result in case of a failed persistence operation.
     *
     * @return A NoContentResult indicating a failed persistence operation with an error.
     */
    @Override
    protected NoContentResult errorResult() {
        final NoContentResult returnValue = new NoContentResult();
        returnValue.setError();
        return returnValue;
    }
}

