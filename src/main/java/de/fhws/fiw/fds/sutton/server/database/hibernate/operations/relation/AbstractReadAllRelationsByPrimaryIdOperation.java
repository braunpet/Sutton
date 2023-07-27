package de.fhws.fiw.fds.sutton.server.database.hibernate.operations.relation;

import de.fhws.fiw.fds.sutton.server.database.hibernate.models.AbstractDBModel;
import de.fhws.fiw.fds.sutton.server.database.hibernate.models.AbstractDBRelation;
import de.fhws.fiw.fds.sutton.server.database.hibernate.models.SuttonColumnConstants;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.AbstractDatabaseOperationWithSearchParameter;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.CollectionModelHibernateResult;
import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This abstract class represents an operation to read all relations of a secondary model associated with a primary model
 * identified by its primaryId. It extends the AbstractDatabaseOperationWithSearchParameter class to support filtering
 * and sorting the results.
 *
 * @param <PrimaryModel>   The type of the primary model entity extending AbstractDBModel.
 * @param <SecondaryModel> The type of the secondary model entity extending AbstractDBModel.
 * @param <Relation>       The type of the relation entity extending AbstractDBRelation.
 */
public abstract class AbstractReadAllRelationsByPrimaryIdOperation<
        PrimaryModel extends AbstractDBModel,
        SecondaryModel extends AbstractDBModel,
        Relation extends AbstractDBRelation>
        extends AbstractDatabaseOperationWithSearchParameter<SecondaryModel, CollectionModelHibernateResult> {

    /**
     * The class of the relation entity associated with the primary and secondary models.
     */
    private final Class<Relation> clazzOfRelation;

    /**
     * The primaryId of the primary model.
     */
    private final long primaryId;

    /**
     * Constructs a new AbstractReadAllRelationsByPrimaryIdOperation with the provided EntityManagerFactory, class of relation,
     * primaryId, and search parameter.
     *
     * @param emf             The EntityManagerFactory used for database access.
     * @param clazzOfRelation The class of the relation entity associated with the primary and secondary models.
     * @param primaryId       The primaryId of the primary model.
     * @param searchParameter The search parameter used for filtering and sorting the results.
     */
    public AbstractReadAllRelationsByPrimaryIdOperation(EntityManagerFactory emf,
                                                        Class<Relation> clazzOfRelation,
                                                        long primaryId,
                                                        SearchParameter searchParameter) {
        super(emf, CollectionModelHibernateResult.class, searchParameter);
        this.clazzOfRelation = clazzOfRelation;
        this.primaryId = primaryId;
    }

    /**
     * Runs the database operation to read all relations of the secondary model associated with the primary model.
     * It retrieves the results, total number of results, and returns them in a CollectionModelHibernateResult object.
     *
     * @return A CollectionModelHibernateResult containing the list of secondary models and the total number of results.
     */
    @Override
    public CollectionModelHibernateResult<SecondaryModel> run() {
        var returnValue = new CollectionModelHibernateResult<>(readResult());
        returnValue.setTotalNumberOfResult(getTotalNumberOfResults());
        return returnValue;
    }

    /**
     * Reads and retrieves the results of the database operation. It creates and executes the criteria query to
     * fetch the relations associated with the primary model. The results are then mapped to the secondary model
     * and returned as a list.
     *
     * @return A list of secondary models representing the relations associated with the primary model.
     */
    protected List<SecondaryModel> readResult() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Relation> find = cb.createQuery(this.clazzOfRelation);
        Root<Relation> rootEntry = find.from(this.clazzOfRelation);
        Join<Relation, SecondaryModel> join = rootEntry.join(SuttonColumnConstants.SECONDARY_MODEL);

        Predicate primaryIdEquals = cb.equal(rootEntry.get(SuttonColumnConstants.DB_RELATION_ID).get(SuttonColumnConstants.PRIMARY_ID), this.primaryId);
        Predicate[] predicatesFromSearchParameter = getPredicates(cb, join);
        find.where(primaryIdEquals, cb.and(predicatesFromSearchParameter))
                .orderBy(getOrderFromSearchParameter(cb, join));
        TypedQuery<Relation> findQuery = em.createQuery(find);
        return findQuery
                .setHint("org.hibernate.cacheable", true)
                .setFirstResult(this.searchParameter.getOffset())
                .setMaxResults(this.searchParameter.getSize())
                .getResultList()
                .stream()
                .map(r -> (SecondaryModel) r.getSecondaryModel())
                .collect(Collectors.toList());
    }

    /**
     * Calculates and retrieves the total number of results that match the specified primary model and search criteria.
     *
     * @return The total number of results matching the primary model and search criteria.
     */
    private int getTotalNumberOfResults() {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        final Root<Relation> rootEntry = cq.from(this.clazzOfRelation);
        Join<Relation, SecondaryModel> join = rootEntry.join(SuttonColumnConstants.SECONDARY_MODEL);

        Predicate primaryIdEquals = cb.equal(rootEntry.get(SuttonColumnConstants.DB_RELATION_ID).get(SuttonColumnConstants.PRIMARY_ID), this.primaryId);

        cq.select(cb.count(rootEntry)).where(primaryIdEquals, cb.and(getPredicates(cb, join)));

        return this.em.createQuery(cq)
                .setHint("org.hibernate.cacheable", true)
                .getSingleResult().intValue();
    }
}
