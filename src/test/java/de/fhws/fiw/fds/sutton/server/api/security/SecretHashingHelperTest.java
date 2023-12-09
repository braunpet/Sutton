package de.fhws.fiw.fds.sutton.server.api.security;

import de.fhws.fiw.fds.sutton.server.api.security.helper.SecretHashingHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SecretHashingHelperTest {

    @Test
    void test_hashPassword() {
        byte[] salt = SecretHashingHelper.getSalt();
        String password = "testPassword";
        String hashedPassword = SecretHashingHelper.hashPassword(password, salt);

        assertNotNull(hashedPassword);
        assertFalse(hashedPassword.isEmpty());
    }

    @Test
    void test_getSalt() {
        byte[] salt = SecretHashingHelper.getSalt();

        assertNotNull(salt);
        assertEquals(16, salt.length);
    }

    @Test
    void test_saltToString_and_stringToSalt() {
        byte[] salt = SecretHashingHelper.getSalt();
        String saltString = SecretHashingHelper.saltToString(salt);
        byte[] convertedSalt = SecretHashingHelper.stringToSalt(saltString);

        assertNotNull(saltString);
        assertArrayEquals(salt, convertedSalt);
    }

    @Test
    void test_verifyPassword_success() {
        byte[] salt = SecretHashingHelper.getSalt();
        String password = "testPassword";
        String hashedPassword = SecretHashingHelper.hashPassword(password, salt);

        assertTrue(SecretHashingHelper.verifyPassword(password, hashedPassword, salt));
    }

    @Test
    void test_verifyPassword_failure() {
        byte[] salt = SecretHashingHelper.getSalt();
        String password = "testPassword";
        String wrongPassword = "wrongPassword";
        String hashedPassword = SecretHashingHelper.hashPassword(password, salt);

        assertFalse(SecretHashingHelper.verifyPassword(wrongPassword, hashedPassword, salt));
    }
}
