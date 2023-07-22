package de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.dao;

import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;
import de.fhws.fiw.fds.sutton.server.database.hibernate.dao.IDatabaseAccessObjectHibernate;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.CollectionModelHibernateResult;
import de.fhws.fiw.fds.suttondemoHibernate.server.database.hibernate.models.PersonDB;

public interface PersonDaoHibernate extends IDatabaseAccessObjectHibernate<PersonDB> {

    CollectionModelHibernateResult<PersonDB> readByFirstNameAndLastName(String firstName, String lastName,
                                                                        SearchParameter searchParameter);

}
