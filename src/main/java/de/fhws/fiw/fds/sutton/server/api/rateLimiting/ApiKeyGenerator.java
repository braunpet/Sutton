package de.fhws.fiw.fds.sutton.server.api.rateLimiting;


import de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.dao.ApiKeyDao;
import de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.dao.ApiKeyDaoAdapter;
import de.fhws.fiw.fds.sutton.server.api.rateLimiting.models.ApiKey;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Collection;

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

    public static ApiKey generateUniqueKey(long resetRateInSeconds, long requestLimit) {
        ApiKeyDao dao = new ApiKeyDaoAdapter();
        Collection<String> apiKeysOnDb = dao.readAll().getResult().stream().map(ApiKey::getApiKey).toList();
        String randomKey = getRandomKey();
        while(apiKeysOnDb.contains(randomKey)) {
            randomKey = getRandomKey();
        }
        return new ApiKey(randomKey, resetRateInSeconds, requestLimit);
    }
}
