package de.fhws.fiw.fds.sutton.server.database.inmemory;

import de.fhws.fiw.fds.sutton.server.database.IDatabaseAccessObject;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import org.apache.commons.lang.ObjectUtils;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * AbstractInMemoryRelationStorage is an abstract generic class that defines the required attributes and methods
 * to create an in-memory storage mechanism to store the relations between the main resources and the sub-resources
 * as instances of {@link AbstractModel}.
 * */
public abstract class AbstractInMemoryRelationStorage<T extends AbstractModel> {

	/**
	 * A {@link MultiValuedMap} with the id of the main resource as a key and
	 * the collection of the related sub-resources as a value
	 * */
	protected MultiValuedMap<Long, Long> storage;

	/**
	 * The default constructor to instantiate the in-memory-relation storage ({@link MultiValuedMap})
	 * */
	protected AbstractInMemoryRelationStorage() {
		this.storage = new HashSetValuedHashMap<>();
	}

	/**
	 * Creates the provided sub-resource in its respective InMemoryStorage and maps it
	 * to the primary resource defined by the provided primaryId
	 * @param primaryId id ({@link Long}) of the primary resource
	 * @param secondary the sub-resource (an instance of {@link AbstractModel})
	 *                     to be created and linked with a primary resource
	 * @return a {@link NoContentResult}
	 * */
	public NoContentResult create(final long primaryId, final T secondary) {
		getSecondaryStorage().create(secondary);
		this.storage.put(primaryId, secondary.getId());
		return new NoContentResult();
	}

	/**
	 * Updates the sub-resource in its respective InMemoryStorage using the
	 * {@link IDatabaseAccessObject#update(AbstractModel)} method and then links it to a
	 * primary resource defined by the provided primaryId if not already linked.
	 * @param primaryId id ({@link Long}) of the primary resource
	 * @param secondary the sub-resource (an instance of {@link AbstractModel})
	 *                     to be updated and linked with the primary resource
	 * @return a {@link NoContentResult}
	 * */
	public NoContentResult update(final long primaryId, final T secondary) {
		getSecondaryStorage().update(secondary);

		if (this.storage.containsMapping(primaryId, secondary.getId()) == false) {
			this.storage.put(primaryId, secondary.getId());
		}

		return new NoContentResult();
	}

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
	public NoContentResult deleteRelation(final long primaryId, final long secondaryId) {
		if (this.storage.containsMapping(primaryId, secondaryId)) {
			this.storage.removeMapping(primaryId, secondaryId);
			return new NoContentResult();
		} else {
			return noMappingError();
		}
	}

	/**
	 * Removes the mapping between a primary resource defined by the provided primaryId and all the sub-resources
	 * linked to it.
	 * @param primaryId id ({@link Long}) of a primary resource to be removed from the inMemoryStorage
	 * @return a {@link NoContentResult}
	 * */
	public NoContentResult deleteRelationsFromPrimary(final long primaryId) {
		this.storage.get(primaryId).stream().forEach(s -> deleteRelation(primaryId, s));
		return new NoContentResult();
	}

	/**
	 * Removes the mapping between each primary resource and the sub-resource defined by the provided
	 * secondaryId
	 * @param secondaryId id ({@link Long}) of the sub-resource to be unlinked from all primary resources
	 * @return a {@link NoContentResult} with an errorCode set to 1 and with an error message
	 * */
	public NoContentResult deleteRelationsToSecondary(final long secondaryId) {
		this.storage.keySet()
				.stream()
				.collect(Collectors.toSet())
				.forEach(primaryId -> deleteRelation(primaryId, secondaryId));

		return noMappingError();
	}

	/**
	 * Checks in the inMemoryStorage if a mapping in the storage between a primary resource defined by the provided primaryId
	 * and the sub-resource defined by the secondaryId exists.
	 * @param primaryId id ({@link Long}) of the primary resource
	 * @param secondaryId id ({@link Long}) of the sub-resource
	 * @return a {@link SingleModelResult} of the sub-resource with its primaryId attribute set to
	 * primaryId param in case there is a mapping to the primary resource
	 * and the sub-resource or an empty {@link SingleModelResult} in case no mapping was detected.
	 * */
	public SingleModelResult<T> readById(final long primaryId, final long secondaryId) {
		if (this.storage.containsMapping(primaryId, secondaryId)) {
			return clone(primaryId, getSecondaryStorage().readById(secondaryId));
		} else {
			return new SingleModelResult<>();
		}
	}

	/**
	 * Filters the collection of sub-resources linked to a primary resource using the provided predicate
	 * @param primaryId id ({@link Long}) of the primary resource
	 * @param predicate {@link Predicate} by which the collection of the sub-resources mapped to a primary resource should be filtered
	 * @return a {@link CollectionModelResult} of all the sub-resources which satisfy the provided predicate with their
	 * primaryId attribute set to the primaryId param referring to the primary resource
	 * */
	protected CollectionModelResult<T> readByPredicate(final long primaryId, final Predicate<T> predicate) {
		return new CollectionModelResult<>(
				clone(primaryId, this.storage.get(primaryId)
						.stream()
						.map(secondaryId -> loadSecondary(secondaryId))
						.filter(result -> result.isEmpty() == false)
						.map(result -> result.getResult())
						.filter(predicate)
						.collect(Collectors.toList())));
	}

	/**
	 * Filters the inMemoryStorage of the sub-resource using to the provided predicate, no matter if they are mapped to the
	 * primary resource or not.
	 * @param primaryId id ({@link Long}) of the primary resource
	 * @param predicate {@link Predicate} by which the storage of the sub-resources should be filtered
	 * @return a {@link CollectionModelResult} of all the sub-resources that satisfy the provided predicate
	 * with their primaryId attribute set to the id of the primary resource
	 * */
	protected CollectionModelResult<T> readAllByPredicate(final long primaryId, final Predicate<T> predicate) {
		return new CollectionModelResult<>(
				clone(primaryId, this.getSecondaryStorage().readAll().getResult().stream()
						.filter(predicate)
						.collect(Collectors.toList())));
	}

	/**
	 * Specifies the InMemoryStorage for the sub-resource
	 * @return an instance of {@link IDatabaseAccessObject}
	 * */
	protected abstract IDatabaseAccessObject<T> getSecondaryStorage();

	private SingleModelResult<T> loadSecondary(final long id) {
		return getSecondaryStorage().readById(id);
	}

	private NoContentResult noMappingError() {
		final NoContentResult errorResult = new NoContentResult();
		errorResult.setError(1, "No mapping between resources");
		return errorResult;
	}

	private SingleModelResult<T> clone(final long primaryId, final SingleModelResult<T> result) {
		return new SingleModelResult<>(clone(primaryId, result.getResult()));
	}

	private CollectionModelResult<T> clone(final long primaryId, final CollectionModelResult<T> result) {
		return new CollectionModelResult<>(clone(primaryId, result.getResult()));
	}

	private Collection<T> clone(final long primaryId, final Collection<T> result) {
		return result.stream().map(e -> clone(primaryId, e)).collect(Collectors.toList());
	}

	private T clone(final long primaryId, final T result) {
		final T clone = (T) ObjectUtils.cloneIfPossible(result);
		clone.setPrimaryId(primaryId);
		return clone;
	}
}
