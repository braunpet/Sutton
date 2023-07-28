package de.fhws.fiw.fds.sutton.server.api.rateLimiting;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class ApiKeyGenerator {

    private static String getRandomKey() {
        SecureRandom sr;
        try {
            sr = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] random = new byte[16];
        sr.nextBytes(random);
        return Base64.getEncoder().encodeToString(random);
    }

    private static String generateUniqueKey() {
        // TODO
        return null;
    }
}
