package de.fhws.fiw.fds.sutton.server.api.security;

import de.fhws.fiw.fds.sutton.server.api.security.models.User;
import org.apache.commons.codec.binary.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.HttpHeaders;
import java.nio.charset.Charset;

/**
 * Helper class to handle Basic Authorization.
 */
public class BasicAuthHelper {

    /**
     * Extracts the username and password from the HTTP request's Authorization header.
     *
     * @param request the HTTP request to extract the username and password from.
     * @return a User object with the username and password from the request.
     * @throws NotAuthorizedException if the request doesn't contain a Basic Authorization header.
     */
    public static User readUserFromHttpHeader(final HttpServletRequest request) {
        final String authHeader = request != null ? request.getHeader(HttpHeaders.AUTHORIZATION) : null;

        if (authHeader != null && authHeader.toLowerCase().startsWith("basic ")) {
            final String withoutBasic = authHeader.replaceFirst("(?i)basic ", "");
            final String userColonPass = decodeBase64(withoutBasic);
            final String[] asArray = userColonPass.split(":", 2);

            if (asArray.length == 2) {
                final String name = asArray[0];
                final String secret = asArray[1];

                return new User(name, secret);
            }
        }

        throw new NotAuthorizedException("");
    }

    /**
     * Decodes a Base64-encoded string.
     *
     * @param value the string to decode.
     * @return the decoded string.
     */
    private static String decodeBase64(final String value) {
        return new String(Base64.decodeBase64(value), Charset.forName("UTF-8"));
    }
}
