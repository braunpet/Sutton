package de.fhws.fiw.fds.sutton.server.api.security.api.queries;

import de.fhws.fiw.fds.sutton.server.api.queries.AbstractRelationQuery;
import de.fhws.fiw.fds.sutton.server.api.queries.PagingBehaviorUsingOffsetSize;
import de.fhws.fiw.fds.sutton.server.api.security.api.queries.attribute_value.AttributeLikeValueRoleName;
import de.fhws.fiw.fds.sutton.server.api.security.database.dao.IAuthDaoSupplier;
import de.fhws.fiw.fds.sutton.server.api.security.models.Role;
import de.fhws.fiw.fds.sutton.server.database.DatabaseException;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;
import de.fhws.fiw.fds.suttondemoHibernate.server.api.models.Person;

public class QueryByRoleNameOfUserLike extends AbstractRelationQuery<Role> implements IAuthDaoSupplier {

    private int waitingTime;

    public QueryByRoleNameOfUserLike(long primaryId, String name, int offset, int size, int waitingTime, String orderByAttributes){
        super(primaryId);
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
        return getUserRoleDao().readAll(this.primaryId, searchParameter);
    }

}
