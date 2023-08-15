package de.fhws.fiw.fds.sutton.server.api.security;

import de.fhws.fiw.fds.sutton.server.api.security.helper.BearerAuthHelper;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BearerAuthHelperTest {

    @Test
    void test_extractBearerToken_success() {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer sampleToken12345");

        String result = BearerAuthHelper.extractBearerToken(mockRequest);

        assertNotNull(result);
        assertEquals("sampleToken12345", result);
    }

    @Test
    void test_extractBearerToken_no_authorization_header() {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);

        String result = BearerAuthHelper.extractBearerToken(mockRequest);

        assertNull(result);
    }

    @Test
    void test_extractBearerToken_invalid_authorization_header() {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Basic sampleToken12345"); // Not a Bearer token

        String result = BearerAuthHelper.extractBearerToken(mockRequest);

        assertNull(result);
    }

    @Test
    void test_extractBearerToken_empty_bearer_token() {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer "); // Empty Bearer token

        String result = BearerAuthHelper.extractBearerToken(mockRequest);

        assertEquals("", result);
    }
}
