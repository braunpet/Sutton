package de.fhws.fiw.fds.sutton.server.api.rateLimiting;


import de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.dao.ApiKeyDao;
import de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.dao.ApiKeyDaoAdapter;
import de.fhws.fiw.fds.sutton.server.api.rateLimiting.models.ApiKey;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Collection;

public class ApiKeyGenerator {

    /**
     * Generates a random API key using a SecureRandom instance with SHA1PRNG algorithm.
     *
     * @return A randomly generated API key as a Base64-encoded string.
     * @throws RuntimeException If the SHA1PRNG algorithm is not available.
     */
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

    /**
     * Generates a unique API key with the specified reset rate and request limit.
     *
     * @param resetRateInSeconds The time in seconds before the rate limit resets for this API key.
     * @param requestLimit       The maximum number of requests allowed within the reset rate period.
     * @return A new {@link ApiKey} instance representing the generated unique API key.
     */
    public static ApiKey generateUniqueKey(long resetRateInSeconds, long requestLimit) {
        ApiKeyDao dao = new ApiKeyDaoAdapter();
        Collection<String> apiKeysOnDb = dao.readAll().getResult().stream().map(ApiKey::getApiKey).toList();
        String randomKey = getRandomKey();
        while (apiKeysOnDb.contains(randomKey)) {
            randomKey = getRandomKey();
        }
        return new ApiKey(randomKey, resetRateInSeconds, requestLimit);
    }
}
