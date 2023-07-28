package de.fhws.fiw.fds.sutton.server.api.security;

/**
 * This represents the required Permission of {@link de.fhws.fiw.fds.sutton.server.api.security.models.Role}
 * to verify an access in {@link AuthenticationProvider}.
 */
public enum RequiredPermission {
    CREATE,
    READ,
    UPDATE,
    DELETE,
    NONE,
    TEST, // IMPORTANT! This Permission is only for testing!!! You will ignore all authentication logic
    ;
}
