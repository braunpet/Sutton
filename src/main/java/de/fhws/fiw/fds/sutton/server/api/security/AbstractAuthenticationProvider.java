package de.fhws.fiw.fds.sutton.server.api.security;

import de.fhws.fiw.fds.sutton.server.api.security.models.User;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;
import java.util.Optional;

/**
 * The AbstractAuthenticationProvider class provides the required functionality to implement the basic authorization as
 * defined in the HTTP 1.0 specification in RFC 7617, where an HTTP user agent has to provide a username and a password
 * to make an HTTP request
 */
public abstract class AbstractAuthenticationProvider {

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
     * @return {@link Optional<User>}  with the user if it was found, or an empty Optional instead
     */
    protected abstract Optional<User> loadUserFromDatabase(String name);

    private User authorizeUser(final User requestingUser, final String... roles) {
        final Optional<User> databaseUser = loadUserFromDatabase(requestingUser.getUserName());

        if (databaseUser.isPresent() == false) {
            throw new NotAuthorizedException("");
        } else if (isBothPasswordsMatch(databaseUser.get(), requestingUser)) {
            final User theUser = databaseUser.get();
            checkRoles(theUser, roles);
            return theUser.cloneWithoutSecret();
        } else {
            throw new NotAuthorizedException("");
        }
    }

    private boolean isBothPasswordsMatch(final User databaseUser, final User requestingUser) {
        return databaseUser.getSecret().equals(requestingUser.getSecret());
    }

    private void checkRoles(final User user, final String... roles) {
        if (roles.length > 0) {
//            if (Arrays.stream(roles).noneMatch(role -> user.getRole().equalsIgnoreCase(role))) {
                throw new ForbiddenException("");
//            }
        }
    }

    // TODO rewrite the whole class
}
