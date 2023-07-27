package de.fhws.fiw.fds.sutton.server.api.security.api.states.role;

import de.fhws.fiw.fds.sutton.server.api.security.database.dao.IAuthDaoSupplier;
import de.fhws.fiw.fds.sutton.server.api.states.AbstractState;
import de.fhws.fiw.fds.sutton.server.api.states.get.AbstractGetCollectionState;
import de.fhws.fiw.fds.sutton.server.api.security.models.Role;

import javax.ws.rs.core.GenericEntity;
import java.util.Collection;
import java.util.List;

import static de.fhws.fiw.fds.sutton.server.database.hibernate.DatabaseInstaller.RoleNames.ADMIN_ROLES;
import static de.fhws.fiw.fds.sutton.server.database.hibernate.DatabaseInstaller.RoleNames.MOD_ROLES;

public class GetAllRoles extends AbstractGetCollectionState<Role>
        implements IAuthDaoSupplier {

    public GetAllRoles(final Builder builder) {
        super(builder);
    }

    protected void defineHttpResponseBody() {
        this.responseBuilder.entity(new GenericEntity<Collection<Role>>(this.result.getResult()) {
        });
    }

    @Override
    protected void authorizeRequest() {

    }

    @Override
    protected void defineTransitionLinks() {
        addLink(RoleUri.REL_PATH, RoleRelTypes.CREATE_ROLE, getAcceptRequestHeader());
    }

    @Override
    protected List<String> getAllowedRoles() {
        return MOD_ROLES;
    }

    public static class Builder extends AbstractGetCollectionStateBuilder<Role> {

        @Override
        public AbstractState build() {
            return new GetAllRoles(this);
        }
    }
}
