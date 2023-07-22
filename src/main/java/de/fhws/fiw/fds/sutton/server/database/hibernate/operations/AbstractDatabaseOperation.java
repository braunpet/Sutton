package de.fhws.fiw.fds.sutton.server.database.hibernate.operations;

import de.fhws.fiw.fds.sutton.server.database.hibernate.models.AbstractDBModel;
import de.fhws.fiw.fds.sutton.server.database.results.AbstractResult;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.lang.reflect.InvocationTargetException;

public abstract class AbstractDatabaseOperation<T extends AbstractDBModel, R extends AbstractResult> {

    /**
     * The EntityManagerFactory used to create EntityManagers for database access.
     */
    protected EntityManager em;

    /**
     * The current EntityManager used for the database operation.
     */
    private EntityManagerFactory emf;

    /**
     * The EntityTransaction used for the current database operation.
     */
    private EntityTransaction transaction;

    /**
     * Constructs a new AbstractDatabaseOperation with the provided EntityManagerFactory.
     *
     * @param emf The EntityManagerFactory used to create EntityManagers.
     */
    protected AbstractDatabaseOperation(EntityManagerFactory emf) {
        this.emf = emf;
    }

    /**
     * Starts the database operation. It creates a new EntityManager, starts a transaction, calls the abstract
     * method run() for performing the database operation, commits the transaction if successful, or rolls back
     * the transaction and returns the error result in case of an exception.
     *
     * @return The result of the database operation.
     */
    public final R start() {
        this.em = this.emf.createEntityManager();

        try {
            this.transaction = this.em.getTransaction();
            this.transaction.begin();
            R result = run();
            this.transaction.commit();
            return result;
        } catch (Exception e) {
            e.printStackTrace();

            if (this.transaction != null) {
                this.transaction.rollback();
            }

            return errorResult();
        } finally {
            this.em.close();
        }
    }

    /**
     * The abstract method for performing the actual database operation.
     *
     * @return The result of the database operation.
     * @throws NoSuchMethodException If a requested method cannot be found during the database operation.
     * @throws InvocationTargetException If an error occurs while invoking a method during the database operation.
     * @throws InstantiationException If an object cannot be instantiated during the database operation.
     * @throws IllegalAccessException If access to a class, field, method, or constructor is not allowed during the database operation.
     */
    protected abstract R run() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

    /**
     * The abstract method for handling the error result in case of an exception during the database operation.
     *
     * @return The error result of the database operation.
     */
    protected abstract R errorResult();

}
