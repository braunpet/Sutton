package de.fhws.fiw.fds.sutton.server.database.hibernate.operations.relation;

import de.fhws.fiw.fds.sutton.server.database.hibernate.models.AbstractDBModel;
import de.fhws.fiw.fds.sutton.server.database.hibernate.models.AbstractDBRelation;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.AbstractDatabaseOperation;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import jakarta.persistence.EntityManagerFactory;

import java.lang.reflect.InvocationTargetException;

/**
 * This abstract class represents an operation to persist a new relation between a primary model and a secondary model
 * based on the primary model's ID. It extends the AbstractDatabaseOperation class to perform the database persistence operation.
 *
 * @param <PrimaryModel> The type of the primary model extending AbstractDBModel.
 * @param <SecondaryModel> The type of the secondary model extending AbstractDBModel.
 * @param <Relation> The type of the relation extending AbstractDBRelation, representing the association between
 *                   the primary and secondary models.
 */
public abstract class AbstractPersistRelationByPrimaryIdOperation<
        PrimaryModel extends AbstractDBModel,
        SecondaryModel extends AbstractDBModel,
        Relation extends AbstractDBRelation<PrimaryModel, SecondaryModel>>
        extends AbstractDatabaseOperation<SecondaryModel, NoContentResult> {

    /**
     * The class of the relation to be persisted.
     */
    private final Class<Relation> clazzOfRelation;

    /**
     * The class of the primary model to which the relation will be associated.
     */
    private final Class<PrimaryModel> clazzOfPrimaryModel;

    /**
     * The ID of the primary model involved in the relation.
     */
    private final long primaryId;

    /**
     * The secondary model to be persisted and associated with the primary model.
     */
    private final SecondaryModel secondaryModel;

    /**
     * Constructs a new AbstractPersistRelationByPrimaryIdOperation with the provided EntityManagerFactory,
     * class of the relation, class of the primary model, primary ID, and the secondary model to be persisted.
     *
     * @param emf The EntityManagerFactory used for database access.
     * @param clazzOfRelation The class of the relation to be persisted.
     * @param clazzOfPrimaryModel The class of the primary model to which the relation will be associated.
     * @param primaryId The ID of the primary model involved in the relation.
     * @param secondaryModel The secondary model to be persisted and associated with the primary model.
     */
    public AbstractPersistRelationByPrimaryIdOperation(EntityManagerFactory emf, Class<Relation> clazzOfRelation, Class<PrimaryModel> clazzOfPrimaryModel, long primaryId, SecondaryModel secondaryModel) {
        super(emf);
        this.clazzOfRelation = clazzOfRelation;
        this.clazzOfPrimaryModel = clazzOfPrimaryModel;
        this.primaryId = primaryId;
        this.secondaryModel = secondaryModel;
    }

    /**
     * Runs the database operation to persist the new relation between the primary model and the secondary model
     * based on the primary model's ID. It creates and executes the persistence operation for the secondary model
     * and sets up the association with the primary model through the relation.
     *
     * @return A NoContentResult indicating successful persistence with no content to return.
     * @throws NoSuchMethodException If a specified method is not found in the class during reflection.
     * @throws InvocationTargetException If an exception occurs during the invocation of the reflected method.
     * @throws InstantiationException If an error occurs during the instantiation of the relation using reflection.
     * @throws IllegalAccessException If the current method does not have access to the definition of the specified class.
     */
    @Override
    protected NoContentResult run() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        PrimaryModel primaryModel = this.em.find(clazzOfPrimaryModel, primaryId);
        this.em.persist(secondaryModel);
        AbstractDBRelation<PrimaryModel, SecondaryModel> relation = clazzOfRelation.getDeclaredConstructor().newInstance();
        relation.setPrimaryModel(primaryModel);
        relation.setSecondaryModel(secondaryModel);
        this.em.merge(relation); // merge is needed because of detached entity exception
        return new NoContentResult();
    }

    /**
     * Provides the error result in case of a failed persistence operation.
     *
     * @return A NoContentResult indicating a failed persistence operation with an error.
     */
    @Override
    protected NoContentResult errorResult() {
        final NoContentResult returnValue = new NoContentResult();
        returnValue.setError();
        return returnValue;
    }

}
