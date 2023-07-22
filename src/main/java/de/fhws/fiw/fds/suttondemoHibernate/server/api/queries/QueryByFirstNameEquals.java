package de.fhws.fiw.fds.suttondemoHibernate.server.api.queries;

import de.fhws.fiw.fds.sutton.server.api.queries.AbstractQuery;
import de.fhws.fiw.fds.sutton.server.database.DatabaseException;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;
import de.fhws.fiw.fds.suttondemoHibernate.server.DaoFactory;
import de.fhws.fiw.fds.suttondemoHibernate.server.api.models.Person;
import de.fhws.fiw.fds.suttondemoHibernate.server.api.queries.searchParameterAttributesEqualsValues.AttributeEqualsValueFirstName;

public class QueryByFirstNameEquals extends AbstractQuery<Person> {

    String firstName;

    public QueryByFirstNameEquals(String firstNameValue) {
        addAttributeEqualValue(new AttributeEqualsValueFirstName(firstNameValue));
    }

    @Override
    protected CollectionModelResult<Person> doExecuteQuery(SearchParameter searchParameter) throws DatabaseException {
        return DaoFactory.getInstance().getPersonDao().readAll(searchParameter);
    }
}
