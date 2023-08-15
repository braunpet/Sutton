package de.fhws.fiw.fds.sutton.server.api.security;

import de.fhws.fiw.fds.sutton.server.api.security.helper.BasicAuthHelper;
import de.fhws.fiw.fds.sutton.server.api.security.models.User;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.HttpHeaders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BasicAuthHelperTest {

    @Test
    void test_readUserFromHttpHeader_success() {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Basic dXNlcm5hbWU6cGFzc3dvcmQ="); // "username:password" encoded in Base64

        User result = BasicAuthHelper.readUserFromHttpHeader(mockRequest);

        assertNotNull(result);
        assertEquals("username", result.getUserName());
        assertEquals("password", result.getSecret());
    }

    @Test
    void test_readUserFromHttpHeader_no_authorization_header() {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);

        assertThrows(NotAuthorizedException.class, () -> BasicAuthHelper.readUserFromHttpHeader(mockRequest));
    }

    @Test
    void test_readUserFromHttpHeader_invalid_authorization_header() {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer dXNlcm5hbWU6cGFzc3dvcmQ="); // Not a Basic auth header

        assertThrows(NotAuthorizedException.class, () -> BasicAuthHelper.readUserFromHttpHeader(mockRequest));
    }

    @Test
    void test_readUserFromHttpHeader_invalid_base64_data() {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Basic dXNlcm5hbWU="); // Only "username" encoded in Base64 without colon

        assertThrows(NotAuthorizedException.class, () -> BasicAuthHelper.readUserFromHttpHeader(mockRequest));
    }

    @Test
    void test_readUserFromHttpHeader_empty_request() {
        assertThrows(NotAuthorizedException.class, () -> BasicAuthHelper.readUserFromHttpHeader(null));
    }
}
