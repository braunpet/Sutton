package de.fhws.fiw.fds.sutton.server.api.rateLimiting;

import de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.dao.IRateLimiterDaoSupplier;
import de.fhws.fiw.fds.sutton.server.api.rateLimiting.models.ApiKey;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class provides rate limiting with {@link ApiKey}s.
 */
public class RateLimiter implements IRateLimiterDaoSupplier {

    /**
     * Checks every 5 seconds whether the rates need to be reset
     */
    public static final RateLimiter DEFAULT = new RateLimiter(5);

    /**
     * Instances a new {@link RateLimiter} with a given checkRate
     *
     * @param checkRate in seconds the resetRate of {@link ApiKey} is checked
     */
    public RateLimiter(long checkRate) {
        Timer resetTimer = new Timer();
        resetTimer.scheduleAtFixedRate(new ResetTask(), checkRate * 1000, checkRate * 1000);
    }

    /**
     * Checks if a Request for the given API-Key String is allowed.
     *
     * @param apiKey of {@link ApiKey}
     * @return a boolean
     * @throws WebApplicationException if no {@link ApiKey} is present on the DB.
     */
    public boolean isRequestAllowed(String apiKey) {
        ApiKey apiKeyDBOnDB = getApiKeyDao().readApiKey(apiKey).getResult();
        if (apiKeyDBOnDB == null) {
            Response errorResponse = Response.status(Response.Status.BAD_REQUEST)
                    .entity("API-Key " + apiKey + " not found.")
                    .build();
            throw new WebApplicationException(errorResponse);
        }

        long currentTimestamp = System.currentTimeMillis();
        if(currentTimestamp - apiKeyDBOnDB.getLastReset() > apiKeyDBOnDB.getResetRateInSeconds() * 1000) {
            apiKeyDBOnDB.setLastReset(currentTimestamp);
            apiKeyDBOnDB.setRequests(0L);
        }

        long requests = apiKeyDBOnDB.getRequests();
        if (requests < apiKeyDBOnDB.getRequestLimit()) {
            apiKeyDBOnDB.setRequests(requests + 1);
            getApiKeyDao().update(apiKeyDBOnDB);
            return true;
        } else {
            return false;
        }
    }

    /**
     * {@link TimerTask} for the {@link RateLimiter} to check if the requests of the {@link ApiKey} must be reset.
     */
    private class ResetTask extends TimerTask {
        @Override
        public void run() {
            Collection<ApiKey> apiKeyDBS = getApiKeyDao().readAll().getResult();
            long currentTimestamp = System.currentTimeMillis();
            apiKeyDBS.forEach(apiKey -> {
                if(currentTimestamp - apiKey.getLastReset() > apiKey.getResetRateInSeconds() * 1000) {
                    apiKey.setRequests(0L);
                    apiKey.setLastReset(currentTimestamp);
                    getApiKeyDao().update(apiKey);
                }
            });
        }
    }

}
