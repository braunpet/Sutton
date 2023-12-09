package de.fhws.fiw.fds.sutton.server.api.security.models;

import com.owlike.genson.annotation.JsonConverter;
import com.owlike.genson.annotation.JsonIgnore;
import de.fhws.fiw.fds.sutton.server.api.converter.JsonServerLinkConverter;
import de.fhws.fiw.fds.sutton.server.api.converter.XmlServerLinkConverter;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.glassfish.jersey.linking.InjectLink;

import javax.ws.rs.core.Link;

/**
 * The User class extends {@link AbstractModel} and describes the users who are making the HTTP requests. The user class
 * is used in the context of HTTP security to authenticate clients when making HTTP requests
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class User extends AbstractModel {

    private String userName;

    private String secret;

    private String salt;

    @InjectLink(
            title = "self",
            value = "/users/${instance.id}",
            rel = "self",
            style = InjectLink.Style.ABSOLUTE,
            type = "application/json")
    @XmlJavaTypeAdapter(XmlServerLinkConverter.class)
    private Link selfLink;

    @InjectLink(
            style = InjectLink.Style.ABSOLUTE,
            value = "users/${instance.id}/roles",
            rel = "getRolesOfUser",
            title = "role",
            type = "application/json"
    )
    @XmlJavaTypeAdapter(XmlServerLinkConverter.class)
    private Link role;

    public User() {
    }

    public User(String userName, String secret) {
        this.userName = userName;
        this.secret = secret;
    }

    public User(final String userName, final String secret, final String salt) {
        this.userName = userName;
        this.secret = secret;
        this.salt = salt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @JsonIgnore
    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @JsonIgnore
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @JsonConverter(JsonServerLinkConverter.class)
    public Link getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(Link selfLink) {
        this.selfLink = selfLink;
    }

    @JsonConverter(JsonServerLinkConverter.class)
    public Link getRole() {
        return role;
    }

    public void setRole(Link role) {
        this.role = role;
    }

    /**
     * Returns a clone of the user without their password
     *
     * @return {@link User} a clone of the user
     */
    public User cloneWithoutSecret() {
        final User returnValue = new User();
        returnValue.id = this.id;
        returnValue.userName = this.userName;
        return returnValue;
    }
}
