package de.fhws.fiw.fds.sutton.server.api.security.database.dao;

import de.fhws.fiw.fds.sutton.server.api.security.database.models.RoleDB;
import de.fhws.fiw.fds.sutton.server.database.hibernate.dao.IDatabaseRelationAccessObjectHibernate;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.CollectionModelHibernateResult;


public interface UserRoleDaoHibernate extends IDatabaseRelationAccessObjectHibernate<RoleDB> {

    CollectionModelHibernateResult<RoleDB> readRolesByUserName(String userName);

}
