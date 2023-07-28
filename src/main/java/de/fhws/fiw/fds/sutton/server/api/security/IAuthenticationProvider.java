package de.fhws.fiw.fds.sutton.server.api.security;

import de.fhws.fiw.fds.sutton.server.api.security.models.User;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotAuthorizedException;
import java.util.List;

public interface IAuthenticationProvider {

    /**
     * Controls access to a resource based on the user's authorization using a Bearer token.
     * If a Bearer token is present, validate it and authorize the user based on it.
     *
     * @param request    the HTTP request.
     * @param requiredPermission the required permission.
     * @param roles      the roles that are allowed to perform the action.
     * @return the authenticated and authorized user.
     * @throws NotAuthorizedException if the user is not authorized.
     */
    default User accessControlWithBearerToken(final HttpServletRequest request, RequiredPermission requiredPermission, final List<String> roles){
        return accessControlWithBearerToken(request, requiredPermission, roles.toArray(new String[0]));
    }

    /**
     * Controls access to a resource based on the user's authorization using a Bearer token.
     * If a Bearer token is present, validate it and authorize the user based on it.
     *
     * @param request    the HTTP request.
     * @param requiredPermission the required permission.
     * @param roles      the roles that are allowed to perform the action.
     * @return the authenticated and authorized user.
     * @throws NotAuthorizedException if the user is not authorized.
     */
    User accessControlWithBearerToken(final HttpServletRequest request, RequiredPermission requiredPermission, final String... roles);

    /**
     * Controls access to a resource based on the user's authorization using Basic Auth.
     * If Basic Auth credentials are present, validate them and authorize the user based on them.
     *
     * @param request    the HTTP request.
     * @param requiredPermission the required permission.
     * @param roles      the roles that are allowed to perform the action.
     * @return the authenticated and authorized user.
     * @throws NotAuthorizedException if the user is not authorized.
     */
    default User accessControlWithBasicAuth(final HttpServletRequest request, RequiredPermission requiredPermission, final List<String> roles) {
        return accessControlWithBasicAuth(request, requiredPermission, roles.toArray(new String[0]));
    }

    /**
     * Controls access to a resource based on the user's authorization using Basic Auth.
     * If Basic Auth credentials are present, validate them and authorize the user based on them.
     *
     * @param request    the HTTP request.
     * @param requiredPermission the required permission.
     * @param roles      the roles that are allowed to perform the action.
     * @return the authenticated and authorized user.
     * @throws NotAuthorizedException if the user is not authorized.
     */
    User accessControlWithBasicAuth(final HttpServletRequest request, RequiredPermission requiredPermission, final String... roles);

    /**
     * Checks if the provided user has the Admin role.
     *
     * @param user the {@link User} object whose roles are to be checked.
     * @return true if the user has the Admin role, false otherwise.
     */
    boolean isAdmin(User user);
}
