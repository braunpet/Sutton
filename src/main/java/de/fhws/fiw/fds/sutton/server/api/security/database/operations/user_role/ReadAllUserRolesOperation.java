package de.fhws.fiw.fds.sutton.server.api.security.database.operations.user_role;

import de.fhws.fiw.fds.sutton.server.api.security.database.models.RoleDB;
import de.fhws.fiw.fds.sutton.server.api.security.database.models.UserDB;
import de.fhws.fiw.fds.sutton.server.api.security.database.models.UserRoleDB;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.relation.AbstractReadAllRelationsByPrimaryIdOperation;
import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;

import java.util.List;

public class ReadAllUserRolesOperation extends AbstractReadAllRelationsByPrimaryIdOperation<UserDB, RoleDB, UserRoleDB> {

    public ReadAllUserRolesOperation(EntityManagerFactory emf, long primaryId, SearchParameter searchParameter) {
        super(emf, UserRoleDB.class, primaryId, searchParameter);
    }

    @Override
    public List<Predicate> getAdditionalPredicates(CriteriaBuilder cb, From from) {
        return null;
    }

}
