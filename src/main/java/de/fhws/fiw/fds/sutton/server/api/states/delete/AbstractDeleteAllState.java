package de.fhws.fiw.fds.sutton.server.api.states.delete;

import de.fhws.fiw.fds.sutton.server.api.security.Permission;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

import javax.ws.rs.core.Response;

/**
 * The AbstractDeleteAllState class extends the {@link AbstractState} class and provides the required methods and
 * properties to delete all resources of a certain type.
 */
public abstract class AbstractDeleteAllState extends AbstractState {

    /**
     * The result of the delete operation.
     */
    protected NoContentResult result;

    /**
     * Constructor for the AbstractDeleteAllState class using the builder pattern.
     *
     * @param builder The builder to use for constructing the state.
     */
    protected AbstractDeleteAllState(AbstractStateBuilder builder) {
        super(builder);
    }

    @Override
    protected Permission getRequiredPermission() {
        return Permission.DELETE;
    }

    /**
     * This method is called to start the delete operation.
     *
     * @return The response to send back to the client.
     */
    @Override
    protected Response buildInternal() {
        configureState();

        authorizeRequest();

        defineTransitionLinks();

        this.result = deleteAll();

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
     * This method should be used to prove if the user is allowed to perform the delete action
     */
    protected abstract void authorizeRequest();

    /**
     * This method should be implemented by subclasses to delete all entities. It should return a NoContentResult
     * indicating the result of the delete operation.
     *
     * @return The result of the delete operation.
     */
    protected abstract NoContentResult deleteAll();

    /**
     * This method is used to define all transition links based on the idea of a REST system as
     * a finite state machine.
     */
    protected abstract void defineTransitionLinks();

    protected Response createResponse() {
        return this.responseBuilder.status(Response.Status.NO_CONTENT).build();
    }

    public static abstract class AbstractDeleteAllStateBuilder<T extends AbstractModel>
            extends AbstractStateBuilder {
    }
}
