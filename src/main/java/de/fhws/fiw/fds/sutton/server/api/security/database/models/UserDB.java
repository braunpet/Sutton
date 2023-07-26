package de.fhws.fiw.fds.sutton.server.api.security.database.models;

import de.fhws.fiw.fds.sutton.server.database.hibernate.models.AbstractDBModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class UserDB extends AbstractDBModel {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String secret;

    public UserDB() {
        // make JPA happy
    }

    public UserDB(String name, String secret) {
        this.name = name;
        this.secret = secret;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
