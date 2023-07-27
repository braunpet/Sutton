package de.fhws.fiw.fds.sutton.server.api.security.database.operations.user;

import de.fhws.fiw.fds.sutton.server.api.security.database.models.UserDB;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.model.AbstractReadSingleOperation;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;

import java.util.List;

public class ReadUserByNameOperation extends AbstractReadSingleOperation<UserDB> {

    private String userName;

    public ReadUserByNameOperation(EntityManagerFactory emf, String userName) {
        super(emf, UserDB.class);
        this.userName = userName;
    }

    @Override
    public List<Predicate> getAdditionalPredicates(CriteriaBuilder cb, From from) {
        return List.of(cb.equal(from.get("userName"), this.userName));
    }
}
