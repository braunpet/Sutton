package de.fhws.fiw.fds.sutton.server.database.hibernate.operations.relation;

import de.fhws.fiw.fds.sutton.server.database.hibernate.models.AbstractDBModel;
import de.fhws.fiw.fds.sutton.server.database.hibernate.models.AbstractDBRelation;
import de.fhws.fiw.fds.sutton.server.database.hibernate.models.SuttonColumnConstants;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.AbstractDatabaseOperation;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.SingleModelHibernateResult;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

/**
 * This abstract class represents an operation to read a specific relation between a primary model and a secondary model
 * based on their primary and secondary IDs. It extends the AbstractDatabaseOperation class to perform the database read operation.
 *
 * @param <PrimaryModel>   The type of the primary model extending AbstractDBModel.
 * @param <SecondaryModel> The type of the secondary model extending AbstractDBModel.
 * @param <Relation>       The type of the relation extending AbstractDBRelation, representing the association between
 *                         the primary and secondary models.
 */
public abstract class AbstractReadRelationByIdOperation<
        PrimaryModel extends AbstractDBModel,
        SecondaryModel extends AbstractDBModel,
        Relation extends AbstractDBRelation>
        extends AbstractDatabaseOperation<SecondaryModel, SingleModelHibernateResult<SecondaryModel>> {

    /**
     * The class of the relation to be read.
     */
    private final Class<Relation> clazzOfRelation;

    /**
     * The ID of the primary model involved in the relation.
     */
    private final long primaryId;

    /**
     * The ID of the secondary model involved in the relation.
     */
    private final long secondaryId;

    /**
     * Constructs a new AbstractReadRelationByIdOperation with the provided EntityManagerFactory,
     * class of the relation, primary ID, and secondary ID.
     *
     * @param emf             The EntityManagerFactory used for database access.
     * @param clazzOfRelation The class of the relation to be read.
     * @param primaryId       The ID of the primary model involved in the relation.
     * @param secondaryId     The ID of the secondary model involved in the relation.
     */
    public AbstractReadRelationByIdOperation(EntityManagerFactory emf, Class<Relation> clazzOfRelation, long primaryId, long secondaryId) {
        super(emf);
        this.clazzOfRelation = clazzOfRelation;
        this.primaryId = primaryId;
        this.secondaryId = secondaryId;
    }

    /**
     * Runs the database operation to read the specific relation between the primary model and the secondary model
     * based on their primary and secondary IDs. It creates and executes the read operation using CriteriaQuery,
     * and returns the result as a SingleModelHibernateResult containing the secondary model if found, or an empty result otherwise.
     *
     * @return A SingleModelHibernateResult representing the result of the read operation.
     */
    @Override
    protected SingleModelHibernateResult<SecondaryModel> run() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Relation> find = cb.createQuery(this.clazzOfRelation);
        Root<Relation> rootEntry = find.from(this.clazzOfRelation);

        Predicate primaryIdEquals = cb.equal(rootEntry.get(SuttonColumnConstants.DB_RELATION_ID).get(SuttonColumnConstants.PRIMARY_ID), this.primaryId);
        Predicate secondaryIdEquals = cb.equal(rootEntry.get(SuttonColumnConstants.DB_RELATION_ID).get(SuttonColumnConstants.SECONDARY_ID), this.secondaryId);
        find.where(primaryIdEquals, secondaryIdEquals);

        Optional<Relation> result = em.createQuery(find).getResultStream().findFirst();
        return result.map(relation -> new SingleModelHibernateResult<>((SecondaryModel) relation.getSecondaryModel()))
                .orElseGet(SingleModelHibernateResult::new);
    }

    /**
     * Provides the error result in case of a failed read operation.
     *
     * @return A SingleModelHibernateResult representing a failed read operation with an error.
     */
    @Override
    protected SingleModelHibernateResult<SecondaryModel> errorResult() {
        final SingleModelHibernateResult<SecondaryModel> returnValue = new SingleModelHibernateResult<>();
        returnValue.setError();
        return returnValue;
    }
}
