package de.fhws.fiw.fds.sutton.server.database.hibernate.operations.relation;

import de.fhws.fiw.fds.sutton.server.database.hibernate.models.AbstractDBModel;
import de.fhws.fiw.fds.sutton.server.database.hibernate.models.AbstractDBRelation;
import de.fhws.fiw.fds.sutton.server.database.hibernate.models.SuttonColumnConstants;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.AbstractDatabaseOperation;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.lang.reflect.InvocationTargetException;

/**
 * This abstract class represents an operation to delete all relations between a secondary model and primary models
 * based on the secondary model's ID. It extends the AbstractDatabaseOperation class to perform the database delete
 * operation.
 *
 * @param <PrimaryModel>   The type of the primary model extending AbstractDBModel.
 * @param <SecondaryModel> The type of the secondary model extending AbstractDBModel.
 * @param <Relation>       The type of the relation extending AbstractDBRelation, representing the association between
 *                         the secondary and primary models.
 */
public abstract class AbstractDeleteAllRelationsBySecondaryIdOperation<
        PrimaryModel extends AbstractDBModel,
        SecondaryModel extends AbstractDBModel,
        Relation extends AbstractDBRelation<PrimaryModel, SecondaryModel>>
        extends AbstractDatabaseOperation<SecondaryModel, NoContentResult> {

    /**
     * The class of the relation to be deleted.
     */
    private final Class<Relation> clazzOfRelation;

    /**
     * The ID of the secondary model to delete all relations for.
     */
    private final long secondaryId;

    /**
     * Constructs a new AbstractDeleteAllRelationsBySecondaryIdOperation with the provided EntityManagerFactory,
     * class of the relation, and secondary ID.
     *
     * @param emf             The EntityManagerFactory used for database access.
     * @param clazzOfRelation The class of the relation to be deleted.
     * @param secondaryId     The ID of the secondary model to delete all relations for.
     */
    public AbstractDeleteAllRelationsBySecondaryIdOperation(EntityManagerFactory emf, Class<Relation> clazzOfRelation, long secondaryId) {
        super(emf);
        this.clazzOfRelation = clazzOfRelation;
        this.secondaryId = secondaryId;
    }

    /**
     * Runs the database operation to delete all relations between the secondary model and primary models
     * based on the secondary model's ID. It creates and executes the criteria delete query to delete the relations.
     *
     * @return A NoContentResult indicating successful deletion with no content to return.
     */
    @Override
    protected NoContentResult run() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaDelete<Relation> delete = cb.createCriteriaDelete(this.clazzOfRelation);
        Root<Relation> rootEntry = delete.from(this.clazzOfRelation);

        Predicate secondaryIdEquals = cb.equal(rootEntry.get(SuttonColumnConstants.DB_RELATION_ID).get(SuttonColumnConstants.SECONDARY_ID), this.secondaryId);
        delete.where(secondaryIdEquals);
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
