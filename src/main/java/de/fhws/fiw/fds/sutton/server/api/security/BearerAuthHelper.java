package de.fhws.fiw.fds.sutton.server.api.security;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;

/**
 * Helper class to handle Bearer Token Authorization.
 */
public class BearerAuthHelper {

    /**
     * Extracts the Bearer token from the HTTP request's Authorization header.
     *
     * @param request the HTTP request to extract the Bearer token from.
     * @return the Bearer token, or null if the request doesn't contain a Bearer token.
     */
    public static String extractBearerToken(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
