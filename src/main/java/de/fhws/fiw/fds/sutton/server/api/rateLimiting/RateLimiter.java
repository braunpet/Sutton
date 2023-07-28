package de.fhws.fiw.fds.sutton.server.api.rateLimiting;

import de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.models.ApiKeyDB;
import de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.operation.ReadAllApiKeysOperation;
import de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.operation.ReadApiKeyOperation;
import de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.operation.UpdateApiKeyOperation;
import de.fhws.fiw.fds.sutton.server.database.hibernate.IDatabaseConnection;
import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class provides rate limiting with {@link ApiKeyDB}s.
 */
public class RateLimiter implements IDatabaseConnection {

    /**
     * Checks every 5 seconds whether the rates need to be reset
     */
    public static final RateLimiter DEFAULT = new RateLimiter(5);

    /**
     * Instances a new {@link RateLimiter} with a given checkRate
     *
     * @param checkRate in seconds the resetRate of {@link ApiKeyDB} is checked
     */
    public RateLimiter(long checkRate) {
        Timer resetTimer = new Timer();
        resetTimer.scheduleAtFixedRate(new ResetTask(), checkRate * 1000, checkRate * 1000);
    }

    /**
     * Checks if a Request for the given API-Key String is allowed.
     *
     * @param apiKey of {@link ApiKeyDB}
     * @return a boolean
     * @throws WebApplicationException if no {@link ApiKeyDB} is present on the DB.
     */
    public boolean isRequestAllowed(String apiKey) {
        ApiKeyDB apiKeyDBOnDB = new ReadApiKeyOperation(SUTTON_EMF, apiKey).start().getResult();
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
            new UpdateApiKeyOperation(SUTTON_EMF, apiKeyDBOnDB).start();
            return true;
        } else {
            return false;
        }
    }

    /**
     * {@link TimerTask} for the {@link RateLimiter} to check if the requests of the {@link ApiKeyDB} must be reset.
     */
    private class ResetTask extends TimerTask {
        @Override
        public void run() {
            Collection<ApiKeyDB> apiKeyDBS = new ReadAllApiKeysOperation(SUTTON_EMF, SearchParameter.DEFAULT).start().getResult();
            long currentTimestamp = System.currentTimeMillis();
            apiKeyDBS.forEach(apiKey -> {
                if(currentTimestamp - apiKey.getLastReset() > apiKey.getResetRateInSeconds() * 1000) {
                    apiKey.setRequests(0L);
                    apiKey.setLastReset(currentTimestamp);
                    new UpdateApiKeyOperation(SUTTON_EMF, apiKey).start();
                }
            });
        }
    }

}
