package de.fhws.fiw.fds.suttondemoHibernate.server.api.queries;

import de.fhws.fiw.fds.sutton.server.api.queries.AbstractRelationQuery;
import de.fhws.fiw.fds.sutton.server.api.queries.PagingBehaviorUsingOffsetSize;
import de.fhws.fiw.fds.sutton.server.database.DatabaseException;
import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.suttondemoHibernate.server.DaoFactory;
import de.fhws.fiw.fds.suttondemoHibernate.server.api.models.Location;
import de.fhws.fiw.fds.suttondemoHibernate.server.api.models.Person;

public class QueryByLocationName extends AbstractRelationQuery<Location> {

    private String cityName;

    private int waitingTime;

    public QueryByLocationName(long primaryId, String cityName, int offset, int size, int waitingTime, String orderByAttributes) {
        super(primaryId);
        this.cityName = cityName;
        this.waitingTime = waitingTime;
        this.pagingBehavior = new PagingBehaviorUsingOffsetSize<Person>(offset, size);
        this.orderByAttributes = orderByAttributes;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    @Override
    protected CollectionModelResult<Location> doExecuteQuery(SearchParameter searchParameter) throws DatabaseException {
        return DaoFactory.getInstance().getPersonLocationDao().readByCityName(this.primaryId, this.cityName, searchParameter);
    }
}
