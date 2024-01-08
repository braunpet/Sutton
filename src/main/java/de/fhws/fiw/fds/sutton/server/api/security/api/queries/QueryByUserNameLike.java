package de.fhws.fiw.fds.sutton.server.api.security.api.queries;

import de.fhws.fiw.fds.sutton.server.api.queries.AbstractQuery;
import de.fhws.fiw.fds.sutton.server.api.queries.PagingBehaviorUsingOffsetSize;
import de.fhws.fiw.fds.sutton.server.api.security.api.queries.attribute_value.AttributeLikeValueUserName;
import de.fhws.fiw.fds.sutton.server.api.security.database.dao.IAuthDaoSupplier;
import de.fhws.fiw.fds.sutton.server.api.security.models.User;
import de.fhws.fiw.fds.sutton.server.database.DatabaseException;
import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;

public class QueryByUserNameLike extends AbstractQuery<User> implements IAuthDaoSupplier {

    private int waitingTime;

    public QueryByUserNameLike(String name, int offset, int size, int waitingTime, String orderByAttributes){
        addAttributeLikeValue(new AttributeLikeValueUserName(name));
        this.waitingTime = waitingTime;
        this.pagingBehavior = new PagingBehaviorUsingOffsetSize<User>(offset, size);
        this.orderByAttributes = orderByAttributes;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    @Override
    protected CollectionModelResult<User> doExecuteQuery(SearchParameter searchParameter) throws DatabaseException {
        return getUserDao().readAll(searchParameter);
    }

}
