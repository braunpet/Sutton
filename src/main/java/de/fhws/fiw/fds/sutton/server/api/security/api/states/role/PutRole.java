package de.fhws.fiw.fds.sutton.server.api.security.api.states.role;

import de.fhws.fiw.fds.sutton.server.api.security.database.dao.IAuthDaoSupplier;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.put.AbstractPutState;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.api.security.models.Role;

import java.util.List;

import static de.fhws.fiw.fds.sutton.server.AbstractDatabaseInstaller.RoleNames.ADMIN_ROLES;

public class PutRole extends AbstractPutState<Role>
        implements IAuthDaoSupplier {

    public PutRole(final Builder builder) {
        super(builder);
    }

    @Override
    protected SingleModelResult<Role> loadModel() {
        return getRoleDao().readById(this.modelToUpdate.getId());
    }

    @Override
    protected NoContentResult updateModel() {
        return getRoleDao().update(this.modelToUpdate);
    }

    @Override
    protected void authorizeRequest() {
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(RoleUri.REL_PATH_ID, RoleRelTypes.GET_SINGLE_ROLE, getAcceptRequestHeader(),
                this.modelToUpdate.getId());
        addLink(RoleUri.REL_PATH, RoleRelTypes.CREATE_ROLE, getAcceptRequestHeader());
    }

    @Override
    protected List<String> getAllowedRoles() {
        return ADMIN_ROLES;
    }

    public static class Builder extends AbstractPutStateBuilder<Role> {
        @Override
        public AbstractState build() {
            return new PutRole(this);
        }
    }
}
