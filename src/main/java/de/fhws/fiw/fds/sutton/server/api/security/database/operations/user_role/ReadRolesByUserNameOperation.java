package de.fhws.fiw.fds.sutton.server.api.security.database.operations.user_role;

import de.fhws.fiw.fds.sutton.server.api.security.database.models.RoleDB;
import de.fhws.fiw.fds.sutton.server.api.security.database.models.UserDB;
import de.fhws.fiw.fds.sutton.server.api.security.database.models.UserRoleDB;
import de.fhws.fiw.fds.sutton.server.database.hibernate.models.SuttonColumnConstants;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.AbstractDatabaseOperation;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.CollectionModelHibernateResult;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

public class ReadRolesByUserNameOperation extends AbstractDatabaseOperation<RoleDB, CollectionModelHibernateResult<RoleDB>> {

    String userName;

    public ReadRolesByUserNameOperation(EntityManagerFactory emf, String userName) {
        super(emf);
        this.userName = userName;
    }

    @Override
    protected CollectionModelHibernateResult<RoleDB> run() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return new CollectionModelHibernateResult<>(readResult());
    }

    @Override
    protected CollectionModelHibernateResult<RoleDB> errorResult() {
        return new CollectionModelHibernateResult.CollectionModelHibernateResultBuilder<RoleDB>()
                .setError()
                .build();
    }

    protected List<RoleDB> readResult() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UserRoleDB> query = cb.createQuery(UserRoleDB.class);
        Root<UserRoleDB> userRoleRoot = query.from(UserRoleDB.class);
        Join<UserRoleDB, UserDB> userJoin = userRoleRoot.join(SuttonColumnConstants.PRIMARY_MODEL);

        query.where(cb.equal(userJoin.get("userName"), this.userName));
        TypedQuery<UserRoleDB> findQuery = em.createQuery(query);
        return findQuery.setHint("org.hibernate.cacheable", true)
                .getResultList()
                .stream()
                .map(UserRoleDB::getSecondaryModel)
                .collect(Collectors.toList());
    }
}
