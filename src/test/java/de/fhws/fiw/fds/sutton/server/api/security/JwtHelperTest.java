package de.fhws.fiw.fds.sutton.server.api.security;

import de.fhws.fiw.fds.sutton.server.api.security.helper.JwtHelper;
import de.fhws.fiw.fds.sutton.server.api.security.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JwtHelperTest {

    @Test
    void test_generateJwt_success() {
        User user = new User("testUser", "testPassword");
        String jwt = JwtHelper.generateJwt(user);

        assertNotNull(jwt);
        assertFalse(jwt.isEmpty());
    }

    @Test
    void test_parseJwt_success() {
        User user = new User("testUser", "testPassword");
        String jwt = JwtHelper.generateJwt(user);

        Jws<Claims> parsedJwt = JwtHelper.parseJwt(jwt);

        assertNotNull(parsedJwt);
        assertEquals("testUser", parsedJwt.getBody().getSubject());
    }

    @Test
    void test_parseJwt_invalid_signature() {
        String invalidJwt = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0VXNlciIsImV4cCI6MTYyODg1NzU1OX0.invalidSignature";

        assertThrows(JwtException.class, () -> JwtHelper.parseJwt(invalidJwt));
    }
}
