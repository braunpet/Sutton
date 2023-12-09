package de.fhws.fiw.fds.sutton.server.api.security.api.states.user_role;

import de.fhws.fiw.fds.sutton.server.api.security.database.dao.IAuthDaoSupplier;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetCollectionRelationState;
import de.fhws.fiw.fds.sutton.server.api.security.models.Role;

import javax.ws.rs.core.GenericEntity;
import java.util.Collection;
import java.util.List;

import static de.fhws.fiw.fds.sutton.server.AbstractDatabaseInstaller.RoleNames.MOD_ROLES;

public class GetAllRolesOfUser extends AbstractGetCollectionRelationState<Role>
        implements IAuthDaoSupplier {

    public GetAllRolesOfUser(final Builder builder) {
        super(builder);
    }

    @Override
    protected void authorizeRequest() {
        // Implement authorization logic here
    }

    @Override
    protected void defineHttpResponseBody() {
        this.responseBuilder.entity(new GenericEntity<Collection<Role>>(this.result.getResult()) {
        });
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(UserRoleUri.REL_PATH,
                UserRoleRelTypes.CREATE_ROLE,
                getAcceptRequestHeader(),
                this.primaryId);

        addLink(UserRoleUri.REL_PATH_SHOW_ALL,
                UserRoleRelTypes.GET_ALL_ROLES,
                getAcceptRequestHeader(),
                this.primaryId);
    }

    @Override
    protected List<String> getAllowedRoles() {
        return MOD_ROLES;
    }

    public static class Builder extends AbstractGetCollectionRelationStateBuilder<Role> {
        @Override
        public AbstractState build() {
            return new GetAllRolesOfUser(this);
        }
    }
}
