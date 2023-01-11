/*
 * Copyright 2019 University of Applied Sciences Würzburg-Schweinfurt, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package de.fhws.fiw.fds.sutton.server.database.inmemory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.apache.commons.lang.ObjectUtils;
import de.fhws.fiw.fds.sutton.server.database.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

/**
 * AbstractInMemoryStorage is an abstract generic class that defines the required attributes and methods
 * in order to create an inMemoryStorage mechanism for all models that define a primary resource and descend from
 * {@link AbstractModel}.
 * */
public abstract class AbstractInMemoryStorage<T extends AbstractModel> {
	/**
	 * a {@link Map} with the id of type {@link Long} of the model as a key
	 * and the model as an instance of {@link AbstractModel} as a value
	 * */
	protected Map<Long, T> storage;

	private AtomicLong nextId;

	protected AbstractInMemoryStorage() {
		this.storage = new HashMap<>();
		this.nextId = new AtomicLong(1l);
	}

	/**
	 * assigns a unique id to the provided model and creates a mapping between them in the inMemoryStorage
	 * @param model an instance of {@link AbstractModel} to be created in the inMemoryStorage
	 * @return    a {@link NoContentResult}
	 * */
	public NoContentResult create(final T model) {
		model.setId(nextId());
		this.storage.put(model.getId(), model);
		return new NoContentResult();
	}

	/**
	 * Searches the inMemoryStorage for a model using the provided id
	 * @param id the id {@link Long} of the model to be searched for in the inMemoryStorage
	 * @return  a {@link SingleModelResult} wrapping the model if founded, otherwise an
	 * empty {@link SingleModelResult}
	 * */
	public SingleModelResult<T> readById(final long id) {
		if (this.storage.containsKey(id)) {
			return new SingleModelResult<>(clone(this.storage.get(id)));
		}
		else {
			return new SingleModelResult<>();
		}
	}

	/**
	 * Returns all resources from the inMemoryStorage
	 * */
	public CollectionModelResult<T> readAll(SearchParameter searchParameter) {
		return this.readByPredicate(all(), searchParameter);
	}

	/**
	 * Searches the inMemoryStorage for all models, which satisfy the given predicate. The method also configures the
	 * paging behavior and sorts the results using the data provided by the {@link SearchParameter} parameter
	 * @param predicate the {@link Predicate}, by which the inMemoryStorage should be filtered
	 * @param searchParameter {@link SearchParameter} contains the required data for setting the paging behavior
	 *                                               and for configuring the sorting criteria
	 * @return a {@link CollectionModelResult} of the found results after filtering the inMemoryStorage using the given
	 * predicate
	 * */
	protected CollectionModelResult<T> readByPredicate(final Predicate<T> predicate,
			final SearchParameter searchParameter) {
		final CollectionModelResult<T> filteredResult =
				new CollectionModelResult<>(filterBy(predicate));
		final CollectionModelResult<T> page = InMemoryPaging.page(filteredResult,
				searchParameter.getOffset(), searchParameter.getSize());

		// TODO: add sorting

		final CollectionModelResult<T> returnValue =
				new CollectionModelResult<>(clone(page.getResult()));
		returnValue.setTotalNumberOfResult(filteredResult.getTotalNumberOfResult());

		return returnValue;
	}

	private Collection<T> filterBy(final Predicate<T> predicate) {
		return this.storage.values().stream().filter(predicate).collect(Collectors.toList());
	}

	/**
	 * Replaces a model (an instance of {@link AbstractModel}) in the inMemoryStorage <strong>if exists</strong>
	 * with the given model
	 * @param model the new updated model (an instance of {@link AbstractModel})
	 * @return a {@link NoContentResult}
	 * */
	public NoContentResult update(final T model) {
		this.storage.put(model.getId(), model);
		return new NoContentResult();
	}

	/**
	 * Deletes a model (an instance of {@link AbstractModel}) from the inMemoryStorage defined by the provided id
	 * @param id id {@link Long} of the model to be deleted
	 * @return a {@link NoContentResult}
	 * */
	public NoContentResult delete(final long id) {
		this.storage.remove(id);
		return new NoContentResult();
	}

	/**
	 * Clears the inMemoryStorage and resets the ids
	 * */
	public void deleteAll() {
		this.storage.clear();
		this.nextId = new AtomicLong(1);
	}

	private final long nextId() {
		return this.nextId.getAndIncrement();
	}

	private Collection<T> clone(final Collection<T> result) {
		return result.stream().map(e -> clone(e)).collect(Collectors.toList());
	}

	private T clone(final T result) {
		final T clone = (T) ObjectUtils.cloneIfPossible(result);
		return clone;
	}

	private Predicate<T> all() {
		return p -> true;
	}

}
