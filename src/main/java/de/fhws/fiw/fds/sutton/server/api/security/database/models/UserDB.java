package de.fhws.fiw.fds.sutton.server.api.security.database.models;

import de.fhws.fiw.fds.sutton.server.database.hibernate.models.AbstractDBModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class UserDB extends AbstractDBModel {

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String secret;

    public UserDB() {
        // make JPA happy
    }

    public UserDB(String userName, String secret) {
        this.userName = userName;
        this.secret = secret;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
