package de.fhws.fiw.fds.sutton.server.api.security.api.states.user_role;

import de.fhws.fiw.fds.sutton.server.api.security.database.dao.IAuthDaoSupplier;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetRelationState;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.api.security.models.Role;

import java.util.List;

import static de.fhws.fiw.fds.sutton.server.database.hibernate.DatabaseInstaller.RoleNames.MOD_ROLES;

public class GetSingleRoleOfUser extends AbstractGetRelationState<Role>
        implements IAuthDaoSupplier {

    public GetSingleRoleOfUser(final Builder builder) {
        super(builder);
    }

    @Override
    protected void authorizeRequest() {

    }

    @Override
    protected SingleModelResult<Role> loadModel() {
        return getRoleDao().readById(this.requestedId);
    }

    @Override
    protected void defineTransitionLinks() {
        addLink(UserRoleUri.REL_PATH_SHOW_ONLY_LINKED,
                UserRoleRelTypes.GET_ALL_LINKED_ROLES,
                getAcceptRequestHeader(),
                this.primaryId);

        if (isUserLinkedToThisRole()) {
            addLink(UserRoleUri.REL_PATH_ID,
                    UserRoleRelTypes.UPDATE_SINGLE_ROLE,
                    getAcceptRequestHeader(),
                    this.primaryId, this.requestedId);

            addLink(UserRoleUri.REL_PATH_ID,
                    UserRoleRelTypes.DELETE_LINK_FROM_USER_TO_ROLE,
                    getAcceptRequestHeader(),
                    this.primaryId, this.requestedId);
        } else {
            addLink(UserRoleUri.REL_PATH_ID,
                    UserRoleRelTypes.CREATE_LINK_FROM_USER_TO_ROLE,
                    getAcceptRequestHeader(),
                    this.primaryId, this.requestedId);
        }
    }

    private boolean isUserLinkedToThisRole() {
        return !getUserRoleDao().readById(this.primaryId, this.requestedId).isEmpty();
    }

    @Override
    protected List<String> getAllowedRoles() {
        return MOD_ROLES;
    }

    public static class Builder extends AbstractGetRelationStateBuilder {
        @Override
        public AbstractState build() {
            return new GetSingleRoleOfUser(this);
        }
    }
}
