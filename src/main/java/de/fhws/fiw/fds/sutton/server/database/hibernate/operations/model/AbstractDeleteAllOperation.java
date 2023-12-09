package de.fhws.fiw.fds.sutton.server.database.hibernate.operations.model;

import de.fhws.fiw.fds.sutton.server.database.hibernate.models.AbstractDBModel;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.AbstractDatabaseOperation;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.Root;

/**
 * This abstract class represents an operation to delete all records of a certain type from the database.
 * It extends the AbstractDatabaseOperation class to perform the database deleteAll operation.
 *
 * @param <T> The type of the entity extending AbstractDBModel to be deleted.
 */
public abstract class AbstractDeleteAllOperation<T extends AbstractDBModel>
        extends AbstractDatabaseOperation<T, NoContentResult> {

    /**
     * The class of the entities to be deleted.
     */
    private Class<T> clazz;

    /**
     * Constructs a new AbstractDeleteAllOperation with the provided EntityManagerFactory and class of the entity.
     *
     * @param emf   The EntityManagerFactory used for database access.
     * @param clazz The class of the entity to be deleted.
     */
    public AbstractDeleteAllOperation(EntityManagerFactory emf, Class<T> clazz) {
        super(emf);
        this.clazz = clazz;
    }

    /**
     * Runs the database operation to delete all records of a certain type.
     * It creates and executes the criteria delete query to delete the records.
     *
     * @return A NoContentResult indicating successful deletion with no content to return.
     */
    @Override
    protected NoContentResult run() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaDelete<T> delete = cb.createCriteriaDelete(this.clazz);
        Root<T> rootEntry = delete.from(this.clazz);
        em.createQuery(delete).executeUpdate();

        return new NoContentResult();
    }

    /**
     * Provides the error result in case of a failed delete operation.
     *
     * @return A NoContentResult indicating a failed delete operation with an error.
     */
    @Override
    protected NoContentResult errorResult() {
        final NoContentResult returnValue = new NoContentResult();
        returnValue.setError();
        return returnValue;
    }
}
