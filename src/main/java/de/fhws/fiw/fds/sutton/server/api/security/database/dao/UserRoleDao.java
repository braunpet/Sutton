package de.fhws.fiw.fds.sutton.server.api.security.database.dao;

import de.fhws.fiw.fds.sutton.server.api.security.models.Role;
import de.fhws.fiw.fds.sutton.server.database.IDatabaseRelationAccessObject;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;

public interface UserRoleDao extends IDatabaseRelationAccessObject<Role> {

    CollectionModelResult<Role> readRolesByUserName(String userName);

}
