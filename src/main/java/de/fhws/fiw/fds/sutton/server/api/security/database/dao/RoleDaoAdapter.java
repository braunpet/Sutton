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

public class RoleDaoAdapter implements RoleDao{

    private RoleDaoHibernate dao = new RoleDaoHibernateImpl();

    @Override
    public NoContentResult create(Role model) {
        final RoleDB dbModel = createFrom(model);
        final NoContentResult returnValue = this.dao.create(dbModel);
        model.setId(dbModel.getId());
        return returnValue;
    }

    @Override
    public SingleModelResult<Role> readById(long id) {
        return createResult(this.dao.readById(id));
    }

    @Override
    public CollectionModelResult<Role> readAll() {
        return this.readAll(new SearchParameter());
    }

    @Override
    public CollectionModelResult<Role> readAll(SearchParameter searchParameter) {
        return createResult(this.dao.readAll(searchParameter));
    }

    @Override
    public NoContentResult update(Role model) {
        return this.dao.update(createFrom(model));
    }

    @Override
    public NoContentResult delete(long id) {
        return this.dao.delete(id);
    }

    private Collection<Role> createFrom(Collection<RoleDB> models) {
        return models.stream().map(m -> createFrom(m)).collect(Collectors.toList());
    }

    private Role createFrom(RoleDB model) {
        final Role returnValue = new Role(model.getName());
        returnValue.setId(model.getId());
        returnValue.setCreatePermission(model.isCreatePermission());
        returnValue.setReadPermission(model.isReadPermission());
        returnValue.setUpdatePermission(model.isUpdatePermission());
        returnValue.setDeletePermission(model.isDeletePermission());
        return returnValue;
    }

    private RoleDB createFrom(Role model) {
        final RoleDB returnValue = new RoleDB(model.getName());
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

}
