package de.fhws.fiw.fds.sutton.server.database.hibernate.operations;

import de.fhws.fiw.fds.sutton.server.database.hibernate.models.AbstractDBModel;
import de.fhws.fiw.fds.sutton.server.database.results.AbstractResult;
import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.*;

import java.lang.reflect.InvocationTargetException;

public abstract class AbstractDatabaseOperation<T extends AbstractDBModel, R extends AbstractResult> {

    protected EntityManager em;
    private EntityManagerFactory emf;

    private EntityTransaction transaction;

    protected AbstractDatabaseOperation(EntityManagerFactory emf) {
        this.emf = emf;
    }

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

    protected abstract R run() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

    protected abstract R errorResult();

    protected Predicate[] getPredicatesFromSearchParameter(CriteriaBuilder cb, From from, SearchParameter searchParameter) {
        return searchParameter.getAttributesEqualsValues().stream()
                .map(attributeValue -> cb.equal(from.get(attributeValue.getSearchByAttribute()), attributeValue.getEqualsValue()))
                .toArray(Predicate[]::new);
    }

}
