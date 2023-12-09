package de.fhws.fiw.fds.sutton.server.api.security.api.queries;

import de.fhws.fiw.fds.sutton.server.api.queries.AbstractQuery;
import de.fhws.fiw.fds.sutton.server.api.queries.PagingBehaviorUsingOffsetSize;
import de.fhws.fiw.fds.sutton.server.api.security.api.queries.attribute_value.AttributeLikeValueRoleName;
import de.fhws.fiw.fds.sutton.server.api.security.database.dao.IAuthDaoSupplier;
import de.fhws.fiw.fds.sutton.server.api.security.models.Role;
import de.fhws.fiw.fds.sutton.server.database.DatabaseException;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;
import de.fhws.fiw.fds.suttondemoHibernate.server.api.models.Person;

public class QueryByRoleNameLike extends AbstractQuery<Role> implements IAuthDaoSupplier {

    private int waitingTime;

    public QueryByRoleNameLike(String name, int offset, int size, int waitingTime, String orderByAttributes){
        addAttributeLikeValue(new AttributeLikeValueRoleName(name));
        this.waitingTime = waitingTime;
        this.pagingBehavior = new PagingBehaviorUsingOffsetSize<Person>(offset, size);
        this.orderByAttributes = orderByAttributes;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    @Override
    protected CollectionModelResult<Role> doExecuteQuery(SearchParameter searchParameter) throws DatabaseException {
        return getRoleDao().readAll(searchParameter);
    }

}
