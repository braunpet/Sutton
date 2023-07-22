package de.fhws.fiw.fds.sutton.server.database.hibernate.operations.model;

import de.fhws.fiw.fds.sutton.server.database.hibernate.models.AbstractDBModel;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.AbstractDatabaseOperation;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import jakarta.persistence.EntityManagerFactory;

/**
 * This abstract class represents an operation to update a database entity.
 * It extends the AbstractDatabaseOperation class to perform the database update operation.
 *
 * @param <T> The type of the entity extending AbstractDBModel to be updated.
 */
public abstract class AbstractUpdateOperation<T extends AbstractDBModel>
        extends AbstractDatabaseOperation<T, NoContentResult> {

    /**
     * The database entity to be updated.
     */
    private T modelToUpdate;

    /**
     * Constructs a new AbstractUpdateOperation with the provided EntityManagerFactory and the database entity to update.
     *
     * @param emf           The EntityManagerFactory used for database access.
     * @param modelToUpdate The database entity to be updated.
     */
    public AbstractUpdateOperation(EntityManagerFactory emf, T modelToUpdate) {
        super(emf);
        this.modelToUpdate = modelToUpdate;
    }

    /**
     * Runs the database operation to update the entity.
     * It uses the EntityManager's merge method to update the entity in the database.
     *
     * @return A NoContentResult indicating successful update with no content to return.
     */
    @Override
    protected NoContentResult run() {
        this.em.merge(this.modelToUpdate);
        return new NoContentResult();
    }

    /**
     * Provides the error result in case of a failed update operation.
     *
     * @return A NoContentResult indicating a failed update operation with an error.
     */
    @Override
    protected NoContentResult errorResult() {
        final NoContentResult returnValue = new NoContentResult();
        returnValue.setError();
        return returnValue;
    }
}

