package de.fhws.fiw.fds.sutton.server.database.hibernate.operations.relation;

import de.fhws.fiw.fds.sutton.server.database.hibernate.models.AbstractDBModel;
import de.fhws.fiw.fds.sutton.server.database.hibernate.models.AbstractDBRelation;
import de.fhws.fiw.fds.sutton.server.database.hibernate.models.SuttonColumnConstants;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.AbstractDatabaseOperationWithSearchParameter;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.SingleModelHibernateResult;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public abstract class AbstractReadSingleRelationOperation <
        PrimaryModel extends AbstractDBModel,
        SecondaryModel extends AbstractDBModel,
        Relation extends AbstractDBRelation>
        extends AbstractDatabaseOperationWithSearchParameter<SecondaryModel, SingleModelHibernateResult> {

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
     * and primaryId.
     *
     * @param emf             The EntityManagerFactory used for database access.
     * @param clazzOfRelation The class of the relation entity associated with the primary and secondary models.
     * @param primaryId       The primaryId of the primary model.
     */
    public AbstractReadSingleRelationOperation(EntityManagerFactory emf,
                                                        Class<Relation> clazzOfRelation,
                                                        long primaryId) {
        super(emf, SingleModelHibernateResult.class, null);
        this.clazzOfRelation = clazzOfRelation;
        this.primaryId = primaryId;
    }

    /**
     * Runs the database operation to read a single {@link AbstractDBRelation#getSecondaryModel()} of type SecondaryModel from the database
     * based on the {@link AbstractDatabaseOperationWithSearchParameter#getAdditionalPredicates}.
     *
     * @return A SingleModelHibernateResult containing the entity read from the database.
     * @throws NoSuchMethodException if the specified method is not found.
     * @throws InvocationTargetException if an error occurs during method invocation.
     * @throws InstantiationException if an object cannot be instantiated.
     * @throws IllegalAccessException if access to the method or field is denied.
     */
    @Override
    protected SingleModelHibernateResult<SecondaryModel> run() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Relation> find = cb.createQuery(this.clazzOfRelation);
        Root<Relation> rootEntry = find.from(this.clazzOfRelation);
        Join<Relation, SecondaryModel> join = rootEntry.join(SuttonColumnConstants.SECONDARY_MODEL);

        Predicate primaryIdEquals = cb.equal(rootEntry.get(SuttonColumnConstants.DB_RELATION_ID).get(SuttonColumnConstants.PRIMARY_ID), this.primaryId);
        Predicate[] predicatesFromSearchParameter = getAdditionalPredicates(cb, join).toArray(new Predicate[0]);
        find.where(primaryIdEquals, cb.and(predicatesFromSearchParameter));
        TypedQuery<Relation> findQuery = em.createQuery(find);

        Optional<Relation> result = findQuery
                .setHint("org.hibernate.cacheable", true)
                .getResultList().stream().findFirst();

        return result.map(relation -> new SingleModelHibernateResult<>((SecondaryModel) relation.getSecondaryModel())).orElseGet(SingleModelHibernateResult::new);
    }
}
