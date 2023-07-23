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

package de.fhws.fiw.fds.sutton.server.api.states.delete;

import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import javax.ws.rs.core.Response;

/**
 * <p>The AbstractDeleteState class extends the {@link AbstractState} class and defines the required
 * properties and methods to implement the DELETE state.</p>
 *
 * <p>Each extending state class has to define a builder class, which must extend
 * {@link AbstractDeleteState.AbstractDeleteStateBuilder}.</p>
 */
public abstract class AbstractDeleteState<T extends AbstractModel> extends AbstractState {

    /**
     * id {@link Long} of the model to be deleted
     */
    protected long modelIdToDelete;

    /**
     * the result {@link SingleModelResult} of searching the model to be deleted in the storage
     */
    protected SingleModelResult<T> modelToDelete;

    /**
     * the result {@link NoContentResult} to return to the client after deleting the model from the storage
     */
    protected NoContentResult resultAfterDelete;

    public AbstractDeleteState(final AbstractDeleteStateBuilder builder) {
        super(builder);
        this.modelIdToDelete = builder.requestedId;
    }

    @Override
    protected Response buildInternal() {
        configureState();

        authorizeRequest();

        this.modelToDelete = loadModel();

        if (this.modelToDelete.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }

        if (clientDoesNotKnowCurrentModelState(this.modelToDelete.getResult())) {
            return Response.status(Response.Status.PRECONDITION_FAILED).build();
        }

        this.resultAfterDelete = deleteModel();

        if(this.resultAfterDelete.getErrorCode() != null){
            return Response.status(this.resultAfterDelete.getErrorCode())
                    .entity(this.resultAfterDelete.getErrorMessage())
                    .build();
        }

        if (this.resultAfterDelete.hasError()) {
            return Response.serverError()
                    .build();
        }

        return createResponse();
    }

    /**
     * This method should be used to prove if the user is allowed to perform the delete action
     */
    protected abstract void authorizeRequest();

    /**
     * Extending classes should use this method to search for the model to be deleted in the storage
     *
     * @return {@link SingleModelResult} - the result of searching the model in the database
     */
    protected abstract SingleModelResult<T> loadModel();

    /**
     * Returns true if the user doesn't have the most recent version of the model
     *
     * @param modelFromDatabase the model from the database so that the user can compare it with
     *                          the model, the user knows about
     */
    protected boolean clientDoesNotKnowCurrentModelState(final AbstractModel modelFromDatabase) {
        return false;
    }

    /**
     * Extending classes should use this method to implement the deletion of the model from the database
     */
    protected abstract NoContentResult deleteModel();

    protected Response createResponse() {
        defineResponseStatus();

        defineHttpResponseBody();

        defineTransitionLinks();

        return this.responseBuilder.build();
    }

    private void defineResponseStatus() {
        this.responseBuilder.status(Response.Status.NO_CONTENT);
    }

    private void defineHttpResponseBody() {
        this.responseBuilder.entity(this.modelToDelete.getResult());
    }

    /**
     * This method is used to define all transition links based on the idea of a REST system as
     * a finite state machine.
     */
    protected abstract void defineTransitionLinks();

    public static abstract class AbstractDeleteStateBuilder extends AbstractState.AbstractStateBuilder {
        /**
         * id {@link Long} of the model to be searched in the database in order to be deleted
         */
        protected long requestedId;

        public AbstractDeleteStateBuilder setRequestedId(final long requestedId) {
            this.requestedId = requestedId;
            return this;
        }
    }

}
