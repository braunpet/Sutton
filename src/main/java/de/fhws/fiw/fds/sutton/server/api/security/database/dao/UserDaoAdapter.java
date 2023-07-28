package de.fhws.fiw.fds.sutton.server.api.security.database.dao;

import de.fhws.fiw.fds.sutton.server.api.security.database.models.UserDB;
import de.fhws.fiw.fds.sutton.server.api.security.helper.SecretHashingHelper;
import de.fhws.fiw.fds.sutton.server.api.security.models.User;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.CollectionModelHibernateResult;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.SingleModelHibernateResult;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserDaoAdapter implements UserDao{

    private UserDaoHibernate dao = new UserDaoHibernateImpl();

    @Override
    public NoContentResult create(User model) {
        final UserDB dbModel = createFrom(model);
        final NoContentResult returnValue = this.dao.create(dbModel);
        model.setId(dbModel.getId());
        return returnValue;
    }

    @Override
    public SingleModelResult<User> readById(long id) {
        return createResult(this.dao.readById(id));
    }

    @Override
    public CollectionModelResult<User> readAll() {
        return this.readAll(new SearchParameter());
    }

    @Override
    public CollectionModelResult<User> readAll(SearchParameter searchParameter) {
        return createResult(this.dao.readAll(searchParameter));
    }

    @Override
    public NoContentResult update(User model) {
        return this.dao.update(createFrom(model));
    }

    @Override
    public NoContentResult delete(long id) {
        return this.dao.delete(id);
    }

    private Collection<User> createFrom(Collection<UserDB> models) {
        return models.stream().map(m -> createFrom(m)).collect(Collectors.toList());
    }

    private User createFrom(UserDB model) {
        if(model == null){
            return null;
        }
        final User returnValue = new User();
        returnValue.setId(model.getId());
        returnValue.setUserName(model.getUserName());
        returnValue.setSecret(model.getSecret());
        returnValue.setSalt(model.getSalt());
        return returnValue;
    }

    private UserDB createFrom(User model) {
        final UserDB returnValue = new UserDB();
        returnValue.setId(model.getId());
        returnValue.setUserName(model.getUserName());

        byte[] salt = SecretHashingHelper.getSalt();
        returnValue.setSecret(SecretHashingHelper.hashPassword(model.getSecret(), salt));
        returnValue.setSalt(SecretHashingHelper.saltToString(salt));
        return returnValue;
    }

    private SingleModelResult<User> createResult(SingleModelHibernateResult<UserDB> result) {
        if (result.hasError()) {
            final SingleModelResult<User> returnValue = new SingleModelResult<>();
            returnValue.setError(result.getErrorCode(), result.getErrorMessage());
            return returnValue;
        } else {
            return new SingleModelResult<>(createFrom(result.getResult()));
        }
    }

    private CollectionModelResult<User> createResult(CollectionModelHibernateResult<UserDB> result) {
        if (result.hasError()) {
            final CollectionModelResult<User> returnValue = new CollectionModelResult<>();
            returnValue.setError(result.getErrorCode(), result.getErrorMessage());
            return returnValue;
        } else {
            final CollectionModelResult<User> returnValue = new CollectionModelResult<>(createFrom(result.getResult()));
            returnValue.setTotalNumberOfResult(result.getTotalNumberOfResult());
            return returnValue;
        }
    }

    @Override
    public SingleModelResult<User> readUserByName(String userName) {
        return new SingleModelResult<>(createFrom(dao.readUserByName(userName).getResult()));
    }
}
