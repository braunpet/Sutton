package de.fhws.fiw.fds.sutton.server.database.hibernate.operations;

import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.hibernate.models.AbstractDBModel;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.CollectionModelHibernateResult;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractDatabaseOrderByOperation< T extends AbstractDBModel>
        extends AbstractDatabaseOperation<T, CollectionModelHibernateResult<T>> {

    protected AbstractDatabaseOrderByOperation(EntityManagerFactory emf) {
        super(emf);
    }

    protected List<Order> getOrderFromSearchParameter(CriteriaBuilder cb, From from, SearchParameter searchParameter) {
        List<Order> orders = new ArrayList<>();
        String[] orderByAttributes = searchParameter.getOrderByAttributes().split(OrderByConstants.DIVIDER);
        List<String> orderStrings = Arrays.stream(orderByAttributes).toList();
        for (String orderString : orderStrings) {
            if (orderString.startsWith(OrderByConstants.ASC)) {
                orders.add(cb.asc(from.get(orderString.split(OrderByConstants.OPERATION_DIVIDER)[1])));
            } else if (orderString.startsWith(OrderByConstants.DESC)) {
                orders.add(cb.desc(from.get(orderString.split(OrderByConstants.OPERATION_DIVIDER)[1])));
            }
        }

        return orders;
    }

    @Override
    protected CollectionModelHibernateResult<T> errorResult() {
        CollectionModelHibernateResult<T> returnValue = new CollectionModelHibernateResult<T>();
        returnValue.setError(400, "The given sort-Attribute doesn't exist. Maybe a misspell? Watch case sensitivity!");
        return returnValue;
    }

    private static class OrderByConstants {
        protected static final String DIVIDER = ",";
        protected static final String ASC = "asc";
        protected static final String DESC = "desc";
        protected static final String OPERATION_DIVIDER = ":";
    }
}
