package de.fhws.fiw.fds.sutton.server.api.security;

import de.fhws.fiw.fds.sutton.server.api.security.database.dao.IAuthDaoSupplier;
import de.fhws.fiw.fds.sutton.server.api.security.models.Role;
import de.fhws.fiw.fds.sutton.server.api.security.models.User;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * The AuthenticationProvider class provides the required functionality to implement the basic authorization as
 * defined in the HTTP 1.0 specification in RFC 7617, where an HTTP user agent has to provide a username and a password
 * to make an HTTP request
 */
public class AuthenticationProvider implements IAuthDaoSupplier {

    /**
     * Extracts the username and the password of the user from the HTTP request, which were sent in the context of
     * the basic authorization, and then it searches the database for the given user using the read information  from
     * the request
     *
     * @param request    the {@link HttpServletRequest} the HTTP request to extract the username and password from
     * @param permission the {@link Permission} which is needed.
     * @param roles      a list of roles {@link String} that are allowed to perform the HTTP request. The method will check
     *                   if the user owns one of this roles
     * @return the {@link User} from the database
     * @throws NotAuthorizedException if the HTTP request doesn't implement basic authorization or if the given
     *                                username or password are not correct
     * @throws ForbiddenException     if the user exists but is not allowed to perform the HTTP request
     */
    public final User accessControl(final HttpServletRequest request, Permission permission, List<String> roles) {
        return accessControl(request, permission, roles.toArray(new String[0]));
    }

    /**
     * Extracts the username and the password of the user from the HTTP request, which were sent in the context of
     * the basic authorization, and then it searches the database for the given user using the read information  from
     * the request
     *
     * @param request    the {@link HttpServletRequest} the HTTP request to extract the username and password from
     * @param permission the {@link Permission} which is needed.
     * @param roles      a list of roles {@link String} that are allowed to perform the HTTP request. The method will check
     *                   if the user owns one of this roles
     * @return the {@link User} from the database
     * @throws NotAuthorizedException if the HTTP request doesn't implement basic authorization or if the given
     *                                username or password are not correct
     * @throws ForbiddenException     if the user exists but is not allowed to perform the HTTP request
     */
    public final User accessControl(final HttpServletRequest request, Permission permission, final String... roles) {
        if(permission.equals(Permission.NONE)) return null;
        final User requestingUser = BasicAuthHelper.readUserFromHttpHeader(request);
        return authorizeUser(requestingUser, permission, roles);
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
