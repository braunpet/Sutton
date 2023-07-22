package de.fhws.fiw.fds.sutton.server.database.hibernate.operations.model;

import de.fhws.fiw.fds.sutton.server.database.hibernate.models.AbstractDBModel;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.AbstractDatabaseOperation;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.Root;

/**
 * This abstract class represents an operation to delete a record from the database based on its ID.
 * It extends the AbstractDatabaseOperation class to perform the database delete operation.
 *
 * @param <T> The type of the entity extending AbstractDBModel to be deleted.
 */
public abstract class AbstractDeleteByIdOperation<T extends AbstractDBModel>
        extends AbstractDatabaseOperation<T, NoContentResult> {

    /**
     * The ID of the record to be deleted.
     */
    private long idToDelete;

    /**
     * The class of the entity to be deleted.
     */
    private Class<T> clazz;

    /**
     * Constructs a new AbstractDeleteByIdOperation with the provided EntityManagerFactory, class of the entity, and ID to delete.
     *
     * @param emf        The EntityManagerFactory used for database access.
     * @param clazz      The class of the entity to be deleted.
     * @param idToDelete The ID of the record to be deleted.
     */
    public AbstractDeleteByIdOperation(EntityManagerFactory emf, Class<T> clazz, long idToDelete) {
        super(emf);
        this.clazz = clazz;
        this.idToDelete = idToDelete;
    }

    /**
     * Runs the database operation to delete a record based on its ID.
     * It creates and executes the criteria delete query to delete the record.
     *
     * @return A NoContentResult indicating successful deletion with no content to return.
     */
    @Override
    protected NoContentResult run() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaDelete<T> delete = cb.createCriteriaDelete(this.clazz);
        Root<T> rootEntry = delete.from(this.clazz);
        delete.where(cb.equal(rootEntry.get("id"), this.idToDelete));
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
