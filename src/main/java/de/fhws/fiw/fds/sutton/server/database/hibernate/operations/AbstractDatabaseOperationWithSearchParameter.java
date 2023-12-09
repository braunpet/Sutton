package de.fhws.fiw.fds.sutton.server.database.hibernate.operations;

import de.fhws.fiw.fds.sutton.server.database.hibernate.models.AbstractDBModel;
import de.fhws.fiw.fds.sutton.server.database.results.AbstractResult;
import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class supports the use of QueryTemplates and also supports sorting.
 * @param <T> The Generic to be searched for and sorted.
 * @param <R> The Generic type of the result returned by the operation.
 */
public abstract class AbstractDatabaseOperationWithSearchParameter<
        T extends AbstractDBModel,
        R extends AbstractResult>
        extends AbstractDatabaseOperation<T, R> {

    /**
     * The {@link SearchParameter} used for filtering the database query.
     */
    protected SearchParameter searchParameter;

    /**
     * The Class of {@link AbstractResult} used to instantiate an ErrorResult.
     */
    protected Class<R> clazzOfResult;

    /**
     * Constructs a new {@link AbstractDatabaseOperationWithSearchParameter} with the provided {@link EntityManagerFactory} and
     * {@link SearchParameter}.
     *
     * @param emf The {@link EntityManagerFactory} to be used for database access.
     * @param clazzOfResult The class of the result to be returned. This should be directly used in new class-constructor.
     * @param searchParameter The {@link SearchParameter} used for filtering the database query.
     */
    public AbstractDatabaseOperationWithSearchParameter(EntityManagerFactory emf, Class<R> clazzOfResult, SearchParameter searchParameter) {
        super(emf);
        this.searchParameter = searchParameter;
        this.clazzOfResult = clazzOfResult;
    }

    /**
     * Retrieves the list of predicates used for filtering the database query. It merges the predicates from
     * getAdditionalPredicates and getPredicatesFromSearchParameter methods.
     *
     * @param cb The CriteriaBuilder used for creating criteria queries.
     * @param from The From object representing the root entity in the criteria query.
     * @return An array of predicates used for filtering the database query.
     */
    protected Predicate[] getPredicates(CriteriaBuilder cb, From from) {
        List<Predicate> listPredicates = getAdditionalPredicates(cb, from) == null ||
                getAdditionalPredicates(cb, from).isEmpty() ? new ArrayList<>() : getAdditionalPredicates(cb, from);
        Predicate[] arrayPredicates = getPredicatesFromSearchParameter(cb, from);
        return mergePredicates(listPredicates, arrayPredicates);
    }

    /**
     * Retrieves the predicates from the search parameter used for filtering the database query.
     *
     * @param cb The CriteriaBuilder used for creating criteria queries.
     * @param from The From object representing the root entity in the criteria query.
     * @return An array of predicates retrieved from the search parameter.
     */
    private Predicate[] getPredicatesFromSearchParameter(CriteriaBuilder cb, From from) {
        List<Predicate> predicatesEquals = searchParameter.getAttributesEqualsValues().stream()
                .map(attributeValue -> cb.equal(from.get(attributeValue.getSearchByAttribute()), attributeValue.getEqualsValue()))
                .collect(Collectors.toList());

        List<Predicate> predicatesLike = searchParameter.attributesLikeValues.stream()
                .map(attributeValue -> cb.like(cb.lower(from.get(attributeValue.getSearchByAttribute())), attributeValue.getEqualsValue().toLowerCase() + "%"))
                .collect(Collectors.toList());

        predicatesEquals.addAll(predicatesLike);

        return predicatesEquals.toArray(new Predicate[0]);
    }

    /**
     * Merges the list of predicates and the array of predicates into a single array of predicates.
     *
     * @param listPredicates The list of predicates obtained from getAdditionalPredicates method.
     * @param arrayPredicates The array of predicates obtained from getPredicatesFromSearchParameter method.
     * @return An array of merged predicates.
     */
    private Predicate[] mergePredicates(List<Predicate> listPredicates, Predicate[] arrayPredicates) {
        List<Predicate> allPredicates = new ArrayList<>(listPredicates);
        allPredicates.addAll(Arrays.asList(arrayPredicates));
        return allPredicates.toArray(new Predicate[0]);
    }

    /**
     * Retrieves the additional predicates used for filtering the database query.
     * This can be null if inherited and no additional Predicates are needed.
     *
     * @param cb The CriteriaBuilder used for creating criteria queries.
     * @param from The From object representing the root entity in the criteria query.
     * @return A list of additional predicates used for filtering the database query.
     */
    public abstract List<Predicate> getAdditionalPredicates(CriteriaBuilder cb, From from);

    /**
     * Retrieves a list of Order objects based on the sort attributes defined in the search parameter.
     *
     * @param cb The CriteriaBuilder used for creating criteria queries.
     * @param from The From object representing the root entity in the criteria query.
     * @return A list of Order objects containing the specified sort order for the query.
     */
    protected List<Order> getOrderFromSearchParameter(CriteriaBuilder cb, From from) {
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

    /**
     * Provides the error result for this operation in case of a sorting attribute that does not exist.
     *
     * @return A CollectionModelHibernateResult containing the error response.
     */
    @Override
    protected R errorResult() {
        R returnValue;
        try {
            returnValue = clazzOfResult.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        returnValue.setError(400, "There is a problem with a given attribute. Maybe a misspell? Watch case sensitivity!");
        return returnValue;
    }

    /**
     * A utility class containing constants for sorting attributes.
     */
    private static class OrderByConstants {
        /**
         * The divider used to separate multiple sort attributes.
         */
        protected static final String DIVIDER = ",";

        /**
         * The constant value representing ascending sort order.
         */
        protected static final String ASC = "asc";

        /**
         * The constant value representing descending sort order.
         */
        protected static final String DESC = "desc";

        /**
         * The divider used to separate the sort attribute and the sorting operation.
         */
        protected static final String OPERATION_DIVIDER = ":";
    }

}
