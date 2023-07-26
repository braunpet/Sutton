package de.fhws.fiw.fds.suttondemoHibernate.server.api.models;

import com.owlike.genson.annotation.JsonConverter;
import de.fhws.fiw.fds.sutton.server.api.converter.JsonServerLinkConverter;
import de.fhws.fiw.fds.sutton.server.api.converter.XmlServerLinkConverter;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;
import de.fhws.fiw.fds.sutton.server.utils.JsonDateTimeConverter;
import de.fhws.fiw.fds.sutton.server.utils.XmlDateTimeConverter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.glassfish.jersey.linking.InjectLink;


import javax.ws.rs.core.Link;
import java.time.LocalDate;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Location extends AbstractModel {

    private String cityName;

    private double longitude;

    private double latitude;

    @XmlJavaTypeAdapter(XmlDateTimeConverter.class)
    private LocalDate visitedOn;

    @InjectLink(
            style = InjectLink.Style.ABSOLUTE,
            value = "/persons/${instance.primaryId}/locations/${instance.id}",
            rel = "self",
            title = "self",
            type = "application/json",
            condition = "${instance.primaryId != 0}"
    )
    @XmlJavaTypeAdapter(XmlServerLinkConverter.class)
    private Link selfLinkOnSecond;

    @InjectLink(
            style = InjectLink.Style.ABSOLUTE,
            value = "/locations/${instance.id}",
            rel = "self",
            title = "self",
            type = "application/json",
            condition = "${instance.primaryId == 0}"
    )
    @XmlJavaTypeAdapter(XmlServerLinkConverter.class)
    private Link selfLinkPrimary;

    public Location() {

    }

    public Location(final String cityName, final double longitude, final double latitude, final LocalDate visitedOn) {
        this.cityName = cityName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.visitedOn = visitedOn;
    }

    @JsonConverter(JsonServerLinkConverter.class)
    public Link getSelfLinkOnSecond() {
        return selfLinkOnSecond;
    }

    public void setSelfLinkOnSecond(final Link selfLinkOnSecond) {
        this.selfLinkOnSecond = selfLinkOnSecond;
    }

    @JsonConverter(JsonServerLinkConverter.class)
    public Link getSelfLinkPrimary() {
        return selfLinkPrimary;
    }

    public void setSelfLinkPrimary(final Link selfLinkPrimary) {
        this.selfLinkPrimary = selfLinkPrimary;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(final String cityName) {
        this.cityName = cityName;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(final double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(final double latitude) {
        this.latitude = latitude;
    }

    @JsonConverter(JsonDateTimeConverter.class)
    public LocalDate getVisitedOn() {
        return visitedOn;
    }

    @JsonConverter(JsonDateTimeConverter.class)
    public void setVisitedOn(final LocalDate visitedOn) {
        this.visitedOn = visitedOn;
    }

}
