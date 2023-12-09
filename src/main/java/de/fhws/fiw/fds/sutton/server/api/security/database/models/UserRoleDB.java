package de.fhws.fiw.fds.sutton.server.api.security.database.models;

import de.fhws.fiw.fds.sutton.server.database.hibernate.models.AbstractDBRelation;
import jakarta.persistence.Entity;

@Entity
public class UserRoleDB extends AbstractDBRelation<UserDB, RoleDB> {

    // marker relation-class with @Entity Annotation

}
