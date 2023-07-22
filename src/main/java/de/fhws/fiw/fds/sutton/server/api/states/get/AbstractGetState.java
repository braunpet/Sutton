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

import de.fhws.fiw.fds.sutton.server.api.hyperlinks.Hyperlinks;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

/**
 * <p>The AbstractGetState class extends the {@link AbstractState} class and defines the required
 * properties and methods to implement the functionality of fetching a single resource from the database.</p>
 *
 * <p>Each extending state class has to define a builder class, which must extend
 * {@link AbstractGetState.AbstractGetStateBuilder}.</p>
 */
public abstract class AbstractGetState<T extends AbstractModel> extends AbstractState {

    /**
     * id {@link Long} of the model to be searched in the database
     */
    protected long requestedId;

    /**
     * The result {@link SingleModelResult} of searching the model in the database
     */
    protected SingleModelResult<T> requestedModel;

    public AbstractGetState(final AbstractGetStateBuilder builder) {
        super(builder);
        this.requestedId = builder.requestedId;
    }

    @Override
    protected Response buildInternal() {
        configureState();

        authorizeRequest();

        this.requestedModel = loadModel();

        if(this.requestedModel.getErrorCode() != null){
            return Response.status(this.requestedModel.getErrorCode())
                    .entity(this.requestedModel.getErrorMessage())
                    .build();
        }

        if (this.requestedModel.hasError()) {
            return Response.serverError()
                    .build();
        }

        if (this.requestedModel.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }

        if (clientKnowsCurrentModelState(this.requestedModel.getResult())) {
            return Response.notModified().build();
        }

        this.responseBuilder = Response.ok();

        return createResponse();
    }

    /**
     * This method should be used to prove if the user is allowed to request a model
     */
    protected abstract void authorizeRequest();

    /**
     * Extending classes should use this method to implement the loading of the requested resource from the database
     */
    protected abstract SingleModelResult<T> loadModel();

    /**
     * Returns true if the user doesn't have the most recent version of the model
     *
     * @param modelFromDatabase the model from the database so that the user can compare it with
     *                          the model, the user knows about
     */
    protected boolean clientKnowsCurrentModelState(final AbstractModel modelFromDatabase) {
        return false;
    }

    protected Response createResponse() {
        defineHttpResponseBody();

        defineHttpCaching();

        defineSelfLink();

        defineTransitionLinks();

        return this.responseBuilder.build();
    }

    protected void defineHttpResponseBody() {
        this.responseBuilder.entity(this.requestedModel.getResult());
    }

    /**
     * This method is used to define the caching behavior. It could also be used to inform the client that the requested
     * resource hasn't been modified using the Etag or the last-modified mechanism. In this case this method should
     * return a response with the status 304.
     */
    protected void defineHttpCaching() {

    }

    /**
     * This method is used to define all transition links based on the idea of a REST system as
     * a finite state machine.
     */
    protected abstract void defineTransitionLinks();

    protected void defineSelfLink() {
        final UriBuilder builder = this.uriInfo.getAbsolutePathBuilder();
        final URI self = builder.build();

        Hyperlinks.addLink(this.responseBuilder, self, "self", getAcceptRequestHeader());
    }

    public static abstract class AbstractGetStateBuilder extends AbstractState.AbstractStateBuilder {
        /**
         * id {@link Long} of the model to be searched in the database
         */
        protected long requestedId;

        public AbstractGetStateBuilder setRequestedId(final long requestedId) {
            this.requestedId = requestedId;
            return this;
        }
    }

}
