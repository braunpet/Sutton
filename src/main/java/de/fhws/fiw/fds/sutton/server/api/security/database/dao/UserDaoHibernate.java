package de.fhws.fiw.fds.sutton.server.api.security.database.dao;

import de.fhws.fiw.fds.sutton.server.api.security.database.models.UserDB;
import de.fhws.fiw.fds.sutton.server.database.hibernate.dao.IDatabaseAccessObjectHibernate;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.SingleModelHibernateResult;

public interface UserDaoHibernate extends IDatabaseAccessObjectHibernate<UserDB> {

    SingleModelHibernateResult<UserDB> readUserByName(String userName);

}
