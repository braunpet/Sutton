package de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.models;

import de.fhws.fiw.fds.sutton.server.database.hibernate.models.AbstractDBModel;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class PersonDB extends AbstractDBModel {

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private String emailAddress;

    public PersonDB() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

}
