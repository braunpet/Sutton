package de.fhws.fiw.fds.sutton.server.api.rateLimiting.models;

import com.owlike.genson.annotation.JsonConverter;
import de.fhws.fiw.fds.sutton.server.api.converter.JsonServerLinkConverter;
import de.fhws.fiw.fds.sutton.server.api.converter.XmlServerLinkConverter;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.glassfish.jersey.linking.InjectLink;

import javax.ws.rs.core.Link;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ApiKey extends AbstractModel {

    private String apiKey;

    private long requests = 0L;

    private long lastReset = System.currentTimeMillis();

    private long resetRateInSeconds;

    private long requestLimit;

    @InjectLink(
            title = "self",
            value = "/apikeys/${instance.id}",
            rel = "self",
            style = InjectLink.Style.ABSOLUTE,
            type = "application/json")
    @XmlJavaTypeAdapter(XmlServerLinkConverter.class)
    private Link selfLink;

    public ApiKey(String apiKey, long resetRateInSeconds, long requestLimit) {
        this.apiKey = apiKey;
        this.resetRateInSeconds = resetRateInSeconds;
        this.requestLimit = requestLimit;
    }

    public ApiKey() {
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

    @JsonConverter(JsonServerLinkConverter.class)
    public Link getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(Link selfLink) {
        this.selfLink = selfLink;
    }
}
