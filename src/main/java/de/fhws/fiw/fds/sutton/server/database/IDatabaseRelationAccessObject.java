package de.fhws.fiw.fds.sutton.server.database;

import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import java.util.function.Predicate;

public interface IDatabaseRelationAccessObject<T extends AbstractModel>
{
	/**
	 * Creates the given sub-resource in the database and maps it
	 * to a primary resource defined by the provided primaryId
	 * @param primaryId id ({@link Long}) of the primary resource
	 * @param secondary the sub-resource (an instance of {@link AbstractModel})
	 *                     to be created and linked with the primary resource
	 * @return a {@link NoContentResult}
	 * */
	NoContentResult create( final long primaryId, final T secondary );

	/**
	 * Updates the sub-resource in the database and links it to a
	 * primary resource defined by the provided primrayId if not already linked.
	 * @param primaryId id ({@link Long}) of the primary resource
	 * @param secondary the sub-resource (an instance of {@link AbstractModel})
	 *                    to be updated and linked with the primary resource
	 * @return a {@link NoContentResult}
	 * */
	NoContentResult update( final long primaryId, final T secondary );

	/**
	 * Removes the mapping between a primary resource defined by the provided primaryId and the sub-resource
	 * defined by secondaryId <strong>in case a mapping already exists</strong>
	 * @param primaryId id ({@link Long}) of the primary resource
	 * @param secondaryId id ({@link Long}) of the sub-resource
	 * @return a regular {@link NoContentResult} in case the mapping between the primary resource and the sub-resource
	 * was successfully removed
	 * or a {@link NoContentResult} with an errorCode set to 1 and an error message: "No mapping between resources"
	 * if no mapping was detected
	 * */
	NoContentResult deleteRelation( final long primaryId, final long secondaryId );

	/**
	 * Removes the mapping between the primary resource defined by the provided primaryId and all the sub-resources
	 * linked to it.
	 * @param primaryId id ({@link Long}) of the primary resource to be removed from the database
	 * @return a {@link NoContentResult}
	 * */
	NoContentResult deleteRelationsFromPrimary( final long primaryId );

	/**
	 * Removes the mapping between each primary resource and the sub-resource defined by the provided
	 * secondaryId
	 * @param secondaryId id ({@link Long}) of the sub-resource to be unlinked from all primary resources
	 * @return a {@link NoContentResult} with an errorCode set to 1 and with an error message
	 * */
	NoContentResult deleteRelationsToSecondary( final long secondaryId );

	/**
	 * Checks in the database if a mapping in the storage between the primary resource defined by the provided primaryId
	 * and the sub-resource defined by the secondaryId exists.
	 * @param primaryId id ({@link Long}) of the primary resource
	 * @param secondaryId id ({@link Long}) of the sub-resource
	 * @return a {@link SingleModelResult} of the sub-resource with its primaryId attribute set to
	 * primaryId param in case there is a mapping to a primary resource
	 * and the sub-resource or an empty {@link SingleModelResult} in case no mapping was detected.
	 * */
	SingleModelResult<T> readById( final long primaryId, final long secondaryId );

	/**
	 * Searches all sub-resources linked to a primary resource, defined by the provided primaryId, using the provided predicate
	 * @param primaryId id ({@link Long}) of the primary resource
	 * @param predicate {@link Predicate} by which the collection of the sub-resources mapped to a primary resource should be filtered
	 * @return a {@link CollectionModelResult} of all the sub-resources which satisfy the provided predicate
	 * */
	CollectionModelResult<T> readByPredicate( final long primaryId, final Predicate<T> predicate );

	/**
	 * Searches the database for all sub-resources using to the provided predicate, no matter if they are mapped to a
	 * primary resource or not.
	 * @param primaryId id ({@link Long}) of the primary resource
	 * @param predicate {@link Predicate} by which the database of the sub-resources should be searched
	 * @return a {@link CollectionModelResult} of all sub-resources that satisfy the provided predicate
	 * */
	CollectionModelResult<T> readAllByPredicate( final long primaryId, final Predicate<T> predicate );
}
