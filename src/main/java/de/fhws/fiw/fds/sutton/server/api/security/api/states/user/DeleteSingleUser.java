package de.fhws.fiw.fds.sutton.server.api.security.api.states.user;

import de.fhws.fiw.fds.sutton.server.api.security.database.dao.IAuthDaoSupplier;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.delete.AbstractDeleteState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.api.security.models.User;

public class DeleteSingleUser extends AbstractDeleteState<User>
        implements IAuthDaoSupplier {

    public DeleteSingleUser(final Builder builder) {
        super(builder);
    }

    @Override
    protected SingleModelResult<User> loadModel() {
        return getUserDao().readById(this.modelIdToDelete);
    }

    @Override
    protected NoContentResult deleteModel() {
        return getUserDao().delete(this.modelIdToDelete);
    }

    @Override
    protected void defineTransitionLinks() {
    }

    @Override
    protected void authorizeRequest() {

    }

    public static class Builder extends AbstractDeleteStateBuilder {
        @Override
        public AbstractState build() {
            return new DeleteSingleUser(this);
        }
    }
}
