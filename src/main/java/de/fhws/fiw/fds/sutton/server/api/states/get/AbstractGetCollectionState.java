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

package de.fhws.fiw.fds.sutton.server.api.states.get;

import de.fhws.fiw.fds.sutton.server.api.queries.AbstractQuery;
import de.fhws.fiw.fds.sutton.server.api.queries.PagingContext;
import de.fhws.fiw.fds.sutton.server.api.security.Permission;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import javax.ws.rs.core.Response;

/**
 * <p>The AbstractGetCollectionState extends the {@link AbstractState} and provides the required methods and
 * properties to request a collection of resources.</p>
 *
 * <p>Each extending state class has to define a builder class, which must extend
 * {@link AbstractGetCollectionState.AbstractGetCollectionStateBuilder}.</p>
 */
public abstract class AbstractGetCollectionState<T extends AbstractModel> extends AbstractState {

    /**
     * The header name {@link String} of the total number of results found in the database to be sent in
     * the response to the client
     */
    public static final String HEADER_TOTALNUMBEROFRESULTS = "X-totalnumberofresults";

    /**
     * The header name {@link String} for the number of the results in the current response page
     */
    public static final String HEADER_NUMBEROFRESULTS = "X-numberofresults";

    /**
     * The query {@link AbstractQuery} to be used to fetch the resources from the database and to set the paging
     * behavior
     */
    protected AbstractQuery<T> query;

    /**
     * the collection {@link CollectionModelResult} of the requested resources to be sent to the client
     */
    protected CollectionModelResult<T> result;

    protected AbstractGetCollectionState(final AbstractGetCollectionStateBuilder<T> builder) {
        super(builder);
        this.query = builder.query;
    }

    @Override
    protected Permission getRequiredPermission() {
        return Permission.READ;
    }

    @Override
    protected Response buildInternal() {
        configureState();

        authorizeRequest();

        this.result = loadModels();

        if (this.result.getErrorCode() != null) {
            return Response.status(this.result.getErrorCode())
                    .entity(this.result.getErrorMessage())
                    .build();
        }

        if (this.result.hasError()) {
            return Response.serverError()
                    .build();
        }

        return createResponse();
    }

    /**
     * This method should be used to prove if the user is allowed to request a model
     */
    protected abstract void authorizeRequest();

    /**
     * Extending classes should use this method to implement the loading of the requested resources
     * from the database
     */
    protected CollectionModelResult<T> loadModels() {
        return this.query.startQuery();
    }

    protected Response createResponse() {
        defineHttpHeaderTotalNumberOfResults();

        defineHttpHeaderNumberOfResults();

        defineHttpResponseBody();

        defineSelfLink();

        definePagingLinks();

        defineTransitionLinks();

        return this.responseBuilder.build();
    }

    protected void defineHttpHeaderTotalNumberOfResults() {
        this.responseBuilder.header(getHeaderForTotalNumberOfResults(), this.result.getTotalNumberOfResult());
    }

    protected String getHeaderForTotalNumberOfResults() {
        return HEADER_TOTALNUMBEROFRESULTS;
    }

    /**
     * Extending classes should use this method to set the body of the response.
     */
    protected abstract void defineHttpResponseBody();

    protected void defineHttpHeaderNumberOfResults() {
        this.responseBuilder.header(getHeaderForNumberOfResults(), this.result.getResult().size());
    }

    protected String getHeaderForNumberOfResults() {
        return HEADER_NUMBEROFRESULTS;
    }

    /**
     * This method is used to define all transition links based on the idea of a REST system as
     * a finite state machine.
     */
    protected abstract void defineTransitionLinks();

    protected void definePagingLinks() {
        final PagingContext pagingContext = createPagingContext();

        this.query.addPrevPageLink(pagingContext);
        this.query.addNextPageLink(pagingContext);
    }

    protected void defineSelfLink() {
        this.query.addSelfLink(createPagingContext());
    }

    private PagingContext createPagingContext() {
        return new PagingContext(this.uriInfo, this.responseBuilder, getAcceptRequestHeader());
    }

    public static abstract class AbstractGetCollectionStateBuilder<T extends AbstractModel>
            extends AbstractStateBuilder {
        protected AbstractQuery<T> query;

        public AbstractGetCollectionStateBuilder setQuery(final AbstractQuery<T> query) {
            this.query = query;
            return this;
        }
    }

}
