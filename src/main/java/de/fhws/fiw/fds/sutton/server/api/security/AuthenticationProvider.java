package de.fhws.fiw.fds.sutton.server.api.security;

import de.fhws.fiw.fds.sutton.server.api.security.database.dao.IAuthDaoSupplier;
import de.fhws.fiw.fds.sutton.server.api.security.models.Role;
import de.fhws.fiw.fds.sutton.server.api.security.models.User;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;
import java.util.Arrays;
import java.util.List;

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
     * @param request the {@link HttpServletRequest} the HTTP request to extract the username and password from
     * @param roles   a list of roles {@link String} that are allowed to perform the HTTP request. The method will check
     *                if the user owns one of this roles
     * @return the {@link User} from the database
     * @throws NotAuthorizedException if the HTTP request doesn't implement basic authorization or if the given
     *                                username or password are not correct
     * @throws ForbiddenException     if the user exists but is not allowed to perform the HTTP request
     */
    public final User accessControl(final HttpServletRequest request, final String... roles) {
        final User requestingUser = BasicAuthHelper.readUserFromHttpHeader(request);
        return authorizeUser(requestingUser, roles);
    }

    /**
     * Searches the database for a specific {@link  User} using the given name
     *
     * @param name {@link String} name of the user to search for in the database
     * @return {@link SingleModelResult<User>}  with the user if it was found, or an empty SingleModelResult instead
     */
    protected SingleModelResult<User> loadUserFromDatabase(String name){
        return getUserDao().readUserByName(name);
    }

    /**
     * Authorizes the user by checking if the user exists in the database and if the provided password matches the stored password.
     *
     * @param requestingUser the user requesting authorization.
     * @param roles the roles that are allowed to perform the HTTP request.
     * @return the user from the database.
     * @throws NotAuthorizedException if the user does not exist in the database or if the provided password does not match the stored password.
     */
    private User authorizeUser(final User requestingUser, final String... roles) {
        final SingleModelResult<User> databaseUser = loadUserFromDatabase(requestingUser.getUserName());

        if (databaseUser.isEmpty()) {
            throw new NotAuthorizedException("");
        } else if (isBothPasswordsMatch(databaseUser.getResult(), requestingUser)) {
            final User theUser = databaseUser.getResult();
            checkRoles(theUser, roles);
            return theUser.cloneWithoutSecret();
        } else {
            throw new NotAuthorizedException("");
        }
    }

    /**
     * Checks if the provided password matches the stored password for the user.
     *
     * @param databaseUser the user from the database.
     * @param requestingUser the user requesting authorization.
     * @return true if the provided password matches the stored password, false otherwise.
     */
    private boolean isBothPasswordsMatch(final User databaseUser, final User requestingUser) {
        return SecretHashingHelper.verifyPassword(requestingUser.getSecret(), databaseUser.getSecret(), SecretHashingHelper.stringToSalt(databaseUser.getSalt()));
    }

    /**
     * Checks if the user has one of the provided roles.
     *
     * @param user the user to check.
     * @param roles the roles to check against.
     * @throws ForbiddenException if the user does not have any of the provided roles.
     */
    private void checkRoles(final User user, final String... roles) {
        if (roles.length > 0) {
            List<String> userRoles = getUserRoleDao().readRolesByUserName(user.getUserName()).getResult()
                    .stream()
                    .map(Role::getRoleName)
                    .toList();

            if (Arrays.stream(roles).noneMatch(userRoles::contains)) {
                throw new ForbiddenException("");
            }
        }
    }
}
