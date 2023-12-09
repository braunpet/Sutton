/*
 * Copyright 2021 University of Applied Sciences Würzburg-Schweinfurt, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

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
public class Person extends AbstractModel {

    private String firstName;
    private String lastName;

    @XmlJavaTypeAdapter(XmlDateTimeConverter.class)
    private LocalDate birthDate;
    private String emailAddress;

    @InjectLink(
            title = "self",
            value = "/persons/${instance.id}",
            rel = "self",
            style = InjectLink.Style.ABSOLUTE,
            type = "application/json")
    @XmlJavaTypeAdapter( XmlServerLinkConverter.class )
    private Link selfLink;

    @InjectLink(
            style = InjectLink.Style.ABSOLUTE,
            value = "persons/${instance.id}/locations",
            rel = "getLocationsOfPerson",
            title = "location",
            type = "application/json"
    )
    @XmlJavaTypeAdapter( XmlServerLinkConverter.class )
    private Link location;

    public Person() {

    }

    public Person(final String firstname, final String lastname, final String emailAddress,
                  final LocalDate birthdate) {
        this.firstName = firstname;
        this.lastName = lastname;
        this.birthDate = birthdate;
        this.emailAddress = emailAddress;
    }

    @JsonConverter(JsonServerLinkConverter.class)
    public Link getSelfLink() {
        return selfLink;
    }
    public void setSelfLink(Link selfLink) {
        this.selfLink = selfLink;
    }

    @JsonConverter(JsonServerLinkConverter.class)
    public Link getLocation() {
        return location;
    }

    public void setLocation(Link location) {
        this.location = location;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    @JsonConverter(JsonDateTimeConverter.class)
    public LocalDate getBirthDate() {
        return birthDate;
    }

    @JsonConverter(JsonDateTimeConverter.class)
    public void setBirthDate(final LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(final String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\''
                + ", lastName='" + lastName + '\''
                + ", birthDate=" + birthDate + '\''
                + ", emailAddress='" + emailAddress + '\''
                + '}';
    }
}
