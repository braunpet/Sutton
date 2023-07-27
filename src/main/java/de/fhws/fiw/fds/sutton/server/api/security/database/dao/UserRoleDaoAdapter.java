package de.fhws.fiw.fds.sutton.server.api.security.database.dao;

import de.fhws.fiw.fds.sutton.server.api.security.database.models.RoleDB;
import de.fhws.fiw.fds.sutton.server.api.security.models.Role;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.CollectionModelHibernateResult;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.SingleModelHibernateResult;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserRoleDaoAdapter implements UserRoleDao{

    UserRoleDaoHibernate dao = new UserRoleDaoHibernateImpl();

    @Override
    public NoContentResult create(long primaryId, Role model) {
        RoleDB dbModel = createFrom(model);
        NoContentResult returnValue = this.dao.create(primaryId, dbModel);
        model.setId(dbModel.getId());
        return returnValue;
    }

    @Override
    public NoContentResult update(long primaryId, Role model) {
        RoleDB dbModel = createFrom(model);
        NoContentResult returnValue = this.dao.update(primaryId, dbModel);
        model.setId(dbModel.getId());
        return returnValue;
    }

    @Override
    public NoContentResult deleteRelation(long primaryId, long secondaryId) {
        return this.dao.deleteRelation(primaryId, secondaryId);
    }

    @Override
    public NoContentResult deleteRelationsFromPrimary(long primaryId) {
        return this.dao.deleteRelationsFromPrimary(primaryId);
    }

    @Override
    public NoContentResult deleteRelationsToSecondary(long secondaryId) {
        return this.dao.deleteRelationsToSecondary(secondaryId);
    }

    @Override
    public SingleModelResult<Role> readById(long primaryId, long secondaryId) {
        return new SingleModelResult<>(createFrom(this.dao.readById(primaryId, secondaryId).getResult()));
    }

    @Override
    public CollectionModelResult<Role> readAll(long primaryId, SearchParameter searchParameter) {
        return createResult(this.dao.readAll(primaryId, searchParameter));
    }

    private Collection<Role> createFrom(Collection<RoleDB> models) {
        return models.stream().map(m -> createFrom(m)).collect(Collectors.toList());
    }

    private Role createFrom(RoleDB model) {
        final Role returnValue = new Role(model.getRoleName());
        returnValue.setId(model.getId());
        returnValue.setCreatePermission(model.isCreatePermission());
        returnValue.setReadPermission(model.isReadPermission());
        returnValue.setUpdatePermission(model.isUpdatePermission());
        returnValue.setDeletePermission(model.isDeletePermission());
        return returnValue;
    }

    private RoleDB createFrom(Role model) {
        final RoleDB returnValue = new RoleDB(model.getRoleName());
        returnValue.setId(model.getId());
        returnValue.setCreatePermission(model.isCreatePermission());
        returnValue.setReadPermission(model.isReadPermission());
        returnValue.setUpdatePermission(model.isUpdatePermission());
        returnValue.setDeletePermission(model.isDeletePermission());
        return returnValue;
    }

    private SingleModelResult<Role> createResult(SingleModelHibernateResult<RoleDB> result) {
        if (result.hasError()) {
            final SingleModelResult<Role> returnValue = new SingleModelResult<>();
            returnValue.setError(result.getErrorCode(), result.getErrorMessage());
            return returnValue;
        } else {
            return new SingleModelResult<>(createFrom(result.getResult()));
        }
    }

    private CollectionModelResult<Role> createResult(CollectionModelHibernateResult<RoleDB> result) {
        if (result.hasError()) {
            final CollectionModelResult<Role> returnValue = new CollectionModelResult<>();
            returnValue.setError(result.getErrorCode(), result.getErrorMessage());
            return returnValue;
        } else {
            final CollectionModelResult<Role> returnValue = new CollectionModelResult<>(createFrom(result.getResult()));
            returnValue.setTotalNumberOfResult(result.getTotalNumberOfResult());
            return returnValue;
        }
    }

    @Override
    public CollectionModelResult<Role> readRolesByUserName(String userName) {
        return createResult(dao.readRolesByUserName(userName));
    }
}
