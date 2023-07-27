package de.fhws.fiw.fds.sutton.server.api.security;

import de.fhws.fiw.fds.sutton.server.api.security.database.dao.IAuthDaoSupplier;
import de.fhws.fiw.fds.sutton.server.api.security.models.Role;
import de.fhws.fiw.fds.sutton.server.api.security.models.User;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * Provides functionality for user authentication and authorization.
 */
public class AuthenticationProvider implements IAuthDaoSupplier {

    public final User accessControl(final HttpServletRequest request, Permission permission, List<String> roles){
        return accessControl(request, permission, roles.toArray(new String[0]));
    }

    /**
     * Controls access to a resource based on the user's authorization.
     * If a Bearer token is present, validate it and authorize the user based on it.
     * If no Bearer token is present, fall back to Basic Auth.
     *
     * @param request the HTTP request.
     * @param permission the required permission.
     * @param roles the roles that are allowed to perform the action.
     * @return the authenticated and authorized user.
     * @throws NotAuthorizedException if the user is not authorized.
     * @throws ForbiddenException if the user is forbidden from performing the action.
     */
    public final User accessControl(final HttpServletRequest request, Permission permission, final String... roles) {
        if(permission.equals(Permission.NONE)) return null;

        String bearerToken = BearerAuthHelper.extractBearerToken(request);
        if (bearerToken != null) {
            User requestingUser = validateBearerToken(bearerToken);
            return authorizeUser(requestingUser, permission, roles);
        } else {
            final User requestingUser = BasicAuthHelper.readUserFromHttpHeader(request);
            return authorizeUser(requestingUser, permission, roles);
        }
    }

    /**
     * Validates a Bearer token and loads the corresponding user from the database.
     *
     * @param bearerToken the Bearer token to validate.
     * @return the loaded user.
     * @throws NotAuthorizedException if the Bearer token is invalid.
     */
    private User validateBearerToken(final String bearerToken) {
        try {
            Jws<Claims> claims = JwtHelper.parseJwt(bearerToken);
            String username = claims.getBody().getSubject();

            SingleModelResult<User> user = loadUserFromDatabase(username);

            if (!user.isEmpty()) {
                return user.getResult();
            } else {
                throw new NotAuthorizedException("");
            }
        } catch (Exception e) {
            throw new NotAuthorizedException("");
        }
    }

    /**
     * Searches the database for a specific {@link  User} using the given name
     *
     * @param name {@link String} name of the user to search for in the database
     * @return {@link SingleModelResult<User>}  with the user if it was found, or an empty SingleModelResult instead
     */
    protected SingleModelResult<User> loadUserFromDatabase(String name) {
        return getUserDao().readUserByName(name);
    }

    /**
     * Authorizes the user by checking if the user exists in the database, if the provided password matches the stored password,
     * and if the user has the required permission for the action.
     *
     * @param requestingUser     the user requesting authorization.
     * @param permissionRequired the {@link Permission} which is needed.
     * @param roles              the roles that are allowed to perform the action.
     * @return the user from the database.
     * @throws NotAuthorizedException if the user does not exist in the database or if the provided password does not match the stored password.
     * @throws ForbiddenException     if the user does not have the required permission for the action.
     */
    private User authorizeUser(final User requestingUser, Permission permissionRequired, final String... roles) {
        final SingleModelResult<User> databaseUser = loadUserFromDatabase(requestingUser.getUserName());

        if (databaseUser.isEmpty()) {
            throw new NotAuthorizedException("");
        } else if (isBothPasswordsMatch(databaseUser.getResult(), requestingUser)) {
            final User theUser = databaseUser.getResult();

            switch (permissionRequired) {
                case READ:
                    checkRolesForRead(theUser, roles);
                    break;
                case CREATE:
                    checkRolesForCreate(theUser, roles);
                    break;
                case UPDATE:
                    checkRolesForUpdate(theUser, roles);
                    break;
                case DELETE:
                    checkRolesForDelete(theUser, roles);
                    break;
                case NONE:
                    return theUser.cloneWithoutSecret();
                default:
                    throw new IllegalArgumentException("Invalid permission: " + permissionRequired.name());
            }

            return theUser.cloneWithoutSecret();
        } else {
            throw new NotAuthorizedException("");
        }
    }

    /**
     * Checks if the provided password matches the stored password for the user.
     *
     * @param databaseUser   the user from the database.
     * @param requestingUser the user requesting authorization.
     * @return true if the provided password matches the stored password, false otherwise.
     */
    private boolean isBothPasswordsMatch(final User databaseUser, final User requestingUser) {
        return SecretHashingHelper.verifyPassword(requestingUser.getSecret(), databaseUser.getSecret(), SecretHashingHelper.stringToSalt(databaseUser.getSalt()));
    }

    /**
     * Checks if the user has one of the provided roles with read permission.
     *
     * @param user  the user to check.
     * @param roles the roles to check against.
     * @throws ForbiddenException if the user does not have any of the provided roles with read permission.
     */
    private void checkRolesForRead(final User user, final String... roles) {
        checkRolesForPermission(user, Role::isReadPermission, roles);
    }

    /**
     * Checks if the user has one of the provided roles with create permission.
     *
     * @param user  the user to check.
     * @param roles the roles to check against.
     * @throws ForbiddenException if the user does not have any of the provided roles with create permission.
     */
    private void checkRolesForCreate(final User user, final String... roles) {
        checkRolesForPermission(user, Role::isCreatePermission, roles);
    }

    /**
     * Checks if the user has one of the provided roles with update permission.
     *
     * @param user  the user to check.
     * @param roles the roles to check against.
     * @throws ForbiddenException if the user does not have any of the provided roles with update permission.
     */
    private void checkRolesForUpdate(final User user, final String... roles) {
        checkRolesForPermission(user, Role::isUpdatePermission, roles);
    }

    /**
     * Checks if the user has one of the provided roles with delete permission.
     *
     * @param user  the user to check.
     * @param roles the roles to check against.
     * @throws ForbiddenException if the user does not have any of the provided roles with delete permission.
     */
    private void checkRolesForDelete(final User user, final String... roles) {
        checkRolesForPermission(user, Role::isDeletePermission, roles);
    }

    /**
     * Checks if the user has one of the provided roles with a specific permission.
     *
     * @param user       the user to check.
     * @param permission the permission to check against.
     * @param roles      the roles to check against.
     * @throws ForbiddenException if the user does not have any of the provided roles with the specific permission.
     */
    private void checkRolesForPermission(final User user, Function<Role, Boolean> permission, final String... roles) {
        if (roles.length > 0) {
            Collection<Role> userRoles = getUserRoleDao().readRolesByUserName(user.getUserName()).getResult();

            boolean hasPermission = userRoles.stream()
                    .anyMatch(role -> Arrays.asList(roles).contains(role.getRoleName()) && permission.apply(role));

            if (!hasPermission) {
                throw new ForbiddenException("");
            }
        }
    }
}
