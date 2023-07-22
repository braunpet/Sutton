/*
 * Copyright 2019 University of Applied Sciences Würzburg-Schweinfurt, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.fhws.fiw.fds.sutton.server.api.queries;

import de.fhws.fiw.fds.sutton.server.database.DatabaseException;
import de.fhws.fiw.fds.sutton.server.database.searchParameter.AbstractAttributeEqualsValue;
import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import java.util.ArrayList;
import java.util.List;

/**
 * the AbstractQuery class is used to set the paging behavior to be used, when the amount of the requested resources
 * is too big to be returned in a single response. The AbstractQuery class sets also the paging links accordingly
 */
public abstract class AbstractQuery<T extends AbstractModel> {

    /**
     * The resulting data {@link CollectionModelResult} from querying the storage to be returned to the client
     */
    protected CollectionModelResult<T> result;

    /**
     * A {@link List} which contains {@link AbstractAttributeEqualsValue}s for which will be searched
     */
    protected List<AbstractAttributeEqualsValue> attributesEqualsValues = new ArrayList<>();

    /**
     * The paging behavior {@link PagingBehavior}  through which the resulting data should be organized and sent back to the client in the response
     */
    protected PagingBehavior pagingBehavior = new OnePageWithAllResults();

    /**
     * The order in which the results will be sorted by the query. This sorting is done in the
     * {@link de.fhws.fiw.fds.sutton.server.database.hibernate.operations.AbstractDatabaseOrderByOperation}.
     * By default, there is no sorting.
     */
    protected String orderByAttributes = "";

    /**
     * Default constructor to instantiate an AbstractQuery
     */
    protected AbstractQuery() {
    }

    /**
     * Adds an {@link AbstractAttributeEqualsValue} to the {@link List} of {@link AbstractQuery#attributesEqualsValues} of the {@link AbstractQuery}
     * @param attributeEqualValue The attributeValue which should be searched for.
     *                          This searching is done in the {@link de.fhws.fiw.fds.sutton.server.database.hibernate.operations.AbstractDatabaseOperation}.
     * @return the same AbstractQuery object, on which the method was called
     */
    public AbstractQuery addAttributeEqualValue(final AbstractAttributeEqualsValue attributeEqualValue){
        this.attributesEqualsValues.add(attributeEqualValue);
        return this;
    }

    /**
     * Sets the {@link AbstractQuery#attributesEqualsValues} to the given one.
     * @param attributesEqualsValues The attributeValues which should be searched for.
     *                          This searching is done in the {@link de.fhws.fiw.fds.sutton.server.database.hibernate.operations.AbstractDatabaseOperation}.
     * @return the same AbstractQuery object, on which the method was called
     */
    public AbstractQuery setAttributesEqualsValues(final List<AbstractAttributeEqualsValue> attributesEqualsValues){
        this.attributesEqualsValues = attributesEqualsValues;
        return this;
    }

    /**
     * Sets the paging behavior of the AbstractQuery to the given one
     *
     * @param pagingBehavior - {@link PagingBehavior} the paging behavior to be used
     * @return the same AbstractQuery object, on which the method was called
     */
    public AbstractQuery setPagingBehavior(final PagingBehavior pagingBehavior) {
        this.pagingBehavior = pagingBehavior;
        return this;
    }

    /**
     * Sets the {@link AbstractQuery#orderByAttributes} to the given one.
     * @param orderByAttributes The order in which the results will be sorted by the query.
     *                          This sorting is done in the {@link de.fhws.fiw.fds.sutton.server.database.hibernate.operations.AbstractDatabaseOrderByOperation}.
     * @return the same AbstractQuery object, on which the method was called
     */
    public AbstractQuery setOrderByAttributes(final String orderByAttributes) {
        this.orderByAttributes = orderByAttributes;
        return this;
    }

    public final CollectionModelResult<T> startQuery() {
        /*
         * DON'T OPTIMIZE THE FOLLOWING TWO LINES. WE NEED THE RESULT IN OTHER METHODS
         * LATER.
         */
        this.result = executeQuery();
        return this.result;
    }

    protected CollectionModelResult<T> executeQuery() {
        CollectionModelResult<T> result;

        try {
            final SearchParameter searchParameter = new SearchParameter();
            searchParameter.setAttributesEqualsValues(this.attributesEqualsValues);
            searchParameter.setOffset(this.pagingBehavior.getOffset());
            searchParameter.setSize(this.pagingBehavior.getSize());
            searchParameter.setOrderByAttributes(this.orderByAttributes);

            result = doExecuteQuery(searchParameter);
        }
        catch (final DatabaseException e) {
            result = new CollectionModelResult<>();
        }

        return result;
    }

    /**
     * Extending classes should use this method to define the query to fetch the data
     * from the database
     *
     * @param searchParameter {@link SearchParameter} the parameter to be used to search for matching results
     *                        in the database
     * @return a {@link CollectionModelResult} of the fetched data from the database
     * @throws DatabaseException
     */
    protected abstract CollectionModelResult<T> doExecuteQuery(SearchParameter searchParameter)
            throws DatabaseException;

    public final void addSelfLink(final PagingContext pagingContext) {
        this.pagingBehavior.addSelfLink(pagingContext);
    }

    public final void addPrevPageLink(final PagingContext pagingContext) {
        this.pagingBehavior.addPrevPageLink(pagingContext);
    }

    public final void addNextPageLink(final PagingContext pagingContext) {
        this.pagingBehavior.addNextPageLink(pagingContext, this.result);
    }

}
