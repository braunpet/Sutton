package de.fhws.fiw.fds.sutton.server.database.hibernate.operations.relation;

import de.fhws.fiw.fds.sutton.server.database.hibernate.models.AbstractDBModel;
import de.fhws.fiw.fds.sutton.server.database.hibernate.models.AbstractDBRelation;
import de.fhws.fiw.fds.sutton.server.database.hibernate.models.SuttonColumnConstants;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.AbstractDatabaseOperation;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.*;

import java.lang.reflect.InvocationTargetException;

/**
 * This abstract class represents an operation to delete a specific relation between a primary model and a secondary model
 * based on their IDs. It extends the AbstractDatabaseOperation class to perform the database delete operation.
 *
 * @param <PrimaryModel>   The type of the primary model extending AbstractDBModel.
 * @param <SecondaryModel> The type of the secondary model extending AbstractDBModel.
 * @param <Relation>       The type of the relation extending AbstractDBRelation, representing the association between
 *                         the primary and secondary models.
 */
public abstract class AbstractDeleteRelationByIdOperation<
        PrimaryModel extends AbstractDBModel,
        SecondaryModel extends AbstractDBModel,
        Relation extends AbstractDBRelation<PrimaryModel, SecondaryModel>>
        extends AbstractDatabaseOperation<SecondaryModel, NoContentResult> {

    /**
     * The class of the relation to be deleted.
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
     * Constructs a new AbstractDeleteRelationByIdOperation with the provided EntityManagerFactory,
     * class of the relation, primary ID, and secondary ID.
     *
     * @param emf             The EntityManagerFactory used for database access.
     * @param clazzOfRelation The class of the relation to be deleted.
     * @param primaryId       The ID of the primary model involved in the relation.
     * @param secondaryId     The ID of the secondary model involved in the relation.
     */
    public AbstractDeleteRelationByIdOperation(EntityManagerFactory emf, Class<Relation> clazzOfRelation, long primaryId, long secondaryId) {
        super(emf);
        this.clazzOfRelation = clazzOfRelation;
        this.primaryId = primaryId;
        this.secondaryId = secondaryId;
    }

    /**
     * Runs the database operation to delete the specific relation between the primary model and the secondary model
     * based on their IDs. It creates and executes the criteria delete query to delete the relation.
     *
     * @return A NoContentResult indicating successful deletion with no content to return.
     */
    @Override
    protected NoContentResult run() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaDelete<Relation> delete = cb.createCriteriaDelete(this.clazzOfRelation);
        Root<Relation> rootEntry = delete.from(this.clazzOfRelation);

        Predicate primaryIdEquals = cb.equal(rootEntry.get(SuttonColumnConstants.DB_RELATION_ID).get(SuttonColumnConstants.PRIMARY_ID), this.primaryId);
        Predicate secondaryIdEquals = cb.equal(rootEntry.get(SuttonColumnConstants.DB_RELATION_ID).get(SuttonColumnConstants.SECONDARY_ID), this.secondaryId);
        delete.where(primaryIdEquals, secondaryIdEquals);
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
