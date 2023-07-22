package de.fhws.fiw.fds.sutton.server.api.rateLimiting;

import de.fhws.fiw.fds.sutton.server.api.rateLimiting.model.APIKey;
import de.fhws.fiw.fds.sutton.server.api.rateLimiting.operation.ReadAPIKeyOperation;
import de.fhws.fiw.fds.sutton.server.api.rateLimiting.operation.ReadAllAPIKeysOperation;
import de.fhws.fiw.fds.sutton.server.api.rateLimiting.operation.UpdateAPIKeyOperation;
import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.hibernate.IDatabaseConnection;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.*;

/**
 * This class provides rate limiting with {@link APIKey}s.
 */
public class RateLimiter implements IDatabaseConnection {

    /**
     * Checks every 5 seconds whether the rates need to be reset
     */
    public static final RateLimiter DEFAULT = new RateLimiter(5);

    /**
     * Instances a new {@link RateLimiter} with a given checkRate
     *
     * @param checkRate in seconds the resetRate of {@link APIKey} is checked
     */
    public RateLimiter(long checkRate) {
        Timer resetTimer = new Timer();
        resetTimer.scheduleAtFixedRate(new ResetTask(), checkRate * 1000, checkRate * 1000);
    }

    /**
     * Checks if a Request for the given API-Key String is allowed.
     *
     * @param apiKey of {@link APIKey}
     * @return a boolean
     * @throws WebApplicationException if no {@link APIKey} is present on the DB.
     */
    public boolean isRequestAllowed(String apiKey) {
        APIKey apiKeyOnDB = new ReadAPIKeyOperation(SUTTON_EMF, apiKey).start().getResult();
        if (apiKeyOnDB == null) {
            Response errorResponse = Response.status(Response.Status.BAD_REQUEST)
                    .entity("API-Key " + apiKey + " not found.")
                    .build();
            throw new WebApplicationException(errorResponse);
        }

        long currentTimestamp = System.currentTimeMillis();
        if(currentTimestamp - apiKeyOnDB.getLastReset() > apiKeyOnDB.getResetRateInSeconds() * 1000) {
            apiKeyOnDB.setLastReset(currentTimestamp);
            apiKeyOnDB.setRequests(0L);
        }

        long requests = apiKeyOnDB.getRequests();
        if (requests < apiKeyOnDB.getRequestLimit()) {
            apiKeyOnDB.setRequests(requests + 1);
            new UpdateAPIKeyOperation(SUTTON_EMF, apiKeyOnDB).start();
            return true;
        } else {
            return false;
        }
    }

    /**
     * {@link TimerTask} for the {@link RateLimiter} to check if the requests of the {@link APIKey} must be reset.
     */
    private class ResetTask extends TimerTask {
        @Override
        public void run() {
            Collection<APIKey> apiKeys = new ReadAllAPIKeysOperation(SUTTON_EMF, SearchParameter.DEFAULT).start().getResult();
            long currentTimestamp = System.currentTimeMillis();
            apiKeys.forEach(apiKey -> {
                if(currentTimestamp - apiKey.getLastReset() > apiKey.getResetRateInSeconds() * 1000) {
                    apiKey.setRequests(0L);
                    apiKey.setLastReset(currentTimestamp);
                    new UpdateAPIKeyOperation(SUTTON_EMF, apiKey).start();
                }
            });
        }
    }

}
