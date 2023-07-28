package de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.models;

import de.fhws.fiw.fds.sutton.server.database.hibernate.models.AbstractDBModel;
import de.fhws.fiw.fds.sutton.server.database.hibernate.models.SuttonColumnConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;


/**
 * This class provides a DB-Table to store API-Keys for the RateLimiter
 */
@Entity
public class ApiKeyDB extends AbstractDBModel {

    @Column(name = SuttonColumnConstants.API_KEY, unique = true)
    private String apiKey;

    @Column(name = SuttonColumnConstants.API_KEY_REQUESTS)
    private long requests = 0L;

    @Column(name = SuttonColumnConstants.API_KEY_LAST_RESET)
    private long lastReset = System.currentTimeMillis();

    @Column(name = SuttonColumnConstants.API_KEY_RESET_RATE_IN_SCONDS)
    private long resetRateInSeconds;

    @Column(name = SuttonColumnConstants.API_KEY_REQUEST_LIMIT)
    private long requestLimit;

    public ApiKeyDB(String apiKey, long resetRateInSeconds, long requestLimit) {
        this.apiKey = apiKey;
        this.resetRateInSeconds = resetRateInSeconds;
        this.requestLimit = requestLimit;
    }

    public ApiKeyDB() {
        // make JPA happy
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public long getRequests() {
        return requests;
    }

    public void setRequests(long requests) {
        this.requests = requests;
    }

    public long getLastReset() {
        return lastReset;
    }

    public void setLastReset(long lastReset) {
        this.lastReset = lastReset;
    }

    public long getResetRateInSeconds() {
        return resetRateInSeconds;
    }

    public void setResetRateInSeconds(long resetRateInSeconds) {
        this.resetRateInSeconds = resetRateInSeconds;
    }

    public long getRequestLimit() {
        return requestLimit;
    }

    public void setRequestLimit(long requestLimit) {
        this.requestLimit = requestLimit;
    }

    @Override
    public String toString() {
        return "APIKey{" +
                "apiKey='" + apiKey + '\'' +
                ", requests=" + requests +
                ", lastReset=" + lastReset +
                ", resetRateInSeconds=" + resetRateInSeconds +
                ", requestLimit=" + requestLimit +
                '}';
    }
}
