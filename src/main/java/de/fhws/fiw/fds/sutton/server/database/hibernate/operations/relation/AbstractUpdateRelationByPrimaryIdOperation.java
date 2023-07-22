package de.fhws.fiw.fds.sutton.server.database.hibernate.operations.relation;

import de.fhws.fiw.fds.sutton.server.database.hibernate.models.AbstractDBModel;
import de.fhws.fiw.fds.sutton.server.database.hibernate.models.AbstractDBRelation;
import de.fhws.fiw.fds.sutton.server.database.hibernate.models.SuttonColumnConstants;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.AbstractDatabaseOperation;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.lang.reflect.InvocationTargetException;

/**
 * This abstract class represents an operation to update a relation between a primary model and a secondary model
 * based on their primary ID. It extends the AbstractDatabaseOperation class to perform the database update operation.
 *
 * @param <PrimaryModel>   The type of the primary model extending AbstractDBModel.
 * @param <SecondaryModel> The type of the secondary model extending AbstractDBModel.
 * @param <Relation>       The type of the relation extending AbstractDBRelation, representing the association between
 *                         the primary and secondary models.
 */
public abstract class AbstractUpdateRelationByPrimaryIdOperation<
        PrimaryModel extends AbstractDBModel,
        SecondaryModel extends AbstractDBModel,
        Relation extends AbstractDBRelation<PrimaryModel, SecondaryModel>>
        extends AbstractDatabaseOperation<SecondaryModel, NoContentResult> {

    /**
     * The class of the relation to be updated.
     */
    private final Class<Relation> clazzOfRelation;

    /**
     * The class of the primary model involved in the relation.
     */
    private final Class<PrimaryModel> clazzOfPrimaryModel;

    /**
     * The ID of the primary model involved in the relation.
     */
    private final long primaryId;

    /**
     * The secondary model to be updated in the relation.
     */
    private final SecondaryModel secondaryModelToUpdate;

    /**
     * Constructs a new AbstractUpdateRelationByPrimaryIdOperation with the provided EntityManagerFactory,
     * class of the relation, class of the primary model, primary ID, and secondary model to be updated.
     *
     * @param emf                    The EntityManagerFactory used for database access.
     * @param clazzOfRelation        The class of the relation to be updated.
     * @param clazzOfPrimaryModel    The class of the primary model involved in the relation.
     * @param primaryId              The ID of the primary model involved in the relation.
     * @param secondaryModelToUpdate The secondary model to be updated in the relation.
     */
    public AbstractUpdateRelationByPrimaryIdOperation(EntityManagerFactory emf, Class<Relation> clazzOfRelation, Class<PrimaryModel> clazzOfPrimaryModel, long primaryId, SecondaryModel secondaryModelToUpdate) {
        super(emf);
        this.clazzOfRelation = clazzOfRelation;
        this.clazzOfPrimaryModel = clazzOfPrimaryModel;
        this.primaryId = primaryId;
        this.secondaryModelToUpdate = secondaryModelToUpdate;
    }

    /**
     * Runs the database operation to update the relation between the primary model and the secondary model
     * based on their primary ID. It first merges the changes in the secondary model to the database.
     * Then, it checks if the relation between the primary and secondary models already exists.
     * If not, it creates and persists a new relation in the database.
     *
     * @return A NoContentResult representing a successful update operation.
     * @throws NoSuchMethodException     if a specified constructor cannot be found.
     * @throws InvocationTargetException if a constructor call throws an exception.
     * @throws InstantiationException    if an application tries to create an instance of an abstract class or interface.
     * @throws IllegalAccessException    if an application tries to reflectively create an instance (other than an array),
     *                                   set or get a field, or invoke a method, but the currently executing method does not have access to the definition of the specified class, field, method or constructor.
     */
    @Override
    protected NoContentResult run() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        em.merge(secondaryModelToUpdate);

        // Only persist new relation if there is none existent
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Relation> find = cb.createQuery(this.clazzOfRelation);
        Root<Relation> rootEntry = find.from(this.clazzOfRelation);

        Predicate primaryIdEquals = cb.equal(rootEntry.get(SuttonColumnConstants.DB_RELATION_ID).get(SuttonColumnConstants.PRIMARY_ID), this.primaryId);
        Predicate secondaryIdEquals = cb.equal(rootEntry.get(SuttonColumnConstants.DB_RELATION_ID).get(SuttonColumnConstants.SECONDARY_ID), secondaryModelToUpdate.getId());
        find.where(primaryIdEquals, secondaryIdEquals);

        Relation relationOnDB = em.createQuery(find).getResultStream().findFirst().orElse(null);

        if (relationOnDB == null) {
            PrimaryModel primaryResource = this.em.find(clazzOfPrimaryModel, primaryId);
            AbstractDBRelation<PrimaryModel, SecondaryModel> relation = clazzOfRelation.getDeclaredConstructor().newInstance();
            relation.setPrimaryModel(primaryResource);
            relation.setSecondaryModel(secondaryModelToUpdate);
            this.em.merge(relation); // merge is needed because of detached entity exception
        }

        return new NoContentResult();
    }

    /**
     * Provides the error result in case of a failed update operation.
     *
     * @return A NoContentResult representing a failed update operation with an error.
     */
    @Override
    protected NoContentResult errorResult() {
        final NoContentResult returnValue = new NoContentResult();
        returnValue.setError();
        return returnValue;
    }

}
