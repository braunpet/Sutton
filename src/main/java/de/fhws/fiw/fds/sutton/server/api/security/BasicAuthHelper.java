package de.fhws.fiw.fds.sutton.server.api.security;

import de.fhws.fiw.fds.sutton.server.api.security.models.User;
import org.apache.commons.codec.binary.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.HttpHeaders;
import java.nio.charset.Charset;

/**
 * The BasicAuthHelper is a helper class to implement the functionality of the basic authorization as defined in the
 * HTTP 1.0 specification in RFC 7617, where an HTTP user agent has to provide a username and a password to make an
 * HTTP request
 */
public class BasicAuthHelper {

    /**
     * extracts the username and the password that were sent within an HTTP request in the context of basic
     * authorization
     *
     * @param request {@link HttpServletRequest} the HTTP request to extract the username and the password from
     * @return a {@link User} with the id and the password from the request
     * @throws NotAuthorizedException if the HTTP request doesn't implement the basic authorization
     */
    public static User readUserFromHttpHeader(final HttpServletRequest request) {
        final String authHeader = request != null ? request.getHeader(HttpHeaders.AUTHORIZATION) : null;

        if (authHeader != null) {
            if (authHeader.toLowerCase().startsWith("basic ")) {
                final String withoutBasic = authHeader.replaceFirst("(?i)basic ", "");
                final String userColonPass = decodeBase64(withoutBasic);
                final String[] asArray = userColonPass.split(":", 2);

                if (asArray.length == 2) {
                    final String id = asArray[0];
                    final String secret = asArray[1];

                    return new User(id, secret);
                }
            }
        }

        throw new NotAuthorizedException("");
    }

    private static String decodeBase64(final String value) {
        return new String(Base64.decodeBase64(value), Charset.forName("UTF-8"));
    }
}
