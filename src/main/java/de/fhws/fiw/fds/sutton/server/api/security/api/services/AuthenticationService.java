package de.fhws.fiw.fds.sutton.server.api.security.api.services;

import de.fhws.fiw.fds.sutton.server.api.security.RequiredPermission;
import de.fhws.fiw.fds.sutton.server.api.security.SuttonAuthenticationProvider;
import de.fhws.fiw.fds.sutton.server.api.security.helper.JwtHelper;
import de.fhws.fiw.fds.sutton.server.api.security.models.User;
import de.fhws.fiw.fds.sutton.server.api.services.AbstractService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static de.fhws.fiw.fds.sutton.server.AbstractDatabaseInstaller.RoleNames.GUEST_ROLES;

@Path("auth")
public class AuthenticationService extends AbstractService {

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response authenticateUser() {
        User user = new SuttonAuthenticationProvider().accessControlWithBasicAuth(this.httpServletRequest, RequiredPermission.NONE, GUEST_ROLES);
        return Response.status(Response.Status.OK).entity(JwtHelper.generateJwt(user)).build();
    }

}
