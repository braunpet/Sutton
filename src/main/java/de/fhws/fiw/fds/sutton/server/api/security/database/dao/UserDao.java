package de.fhws.fiw.fds.sutton.server.api.security.database.dao;

import de.fhws.fiw.fds.sutton.server.api.security.models.User;
import de.fhws.fiw.fds.sutton.server.database.IDatabaseAccessObject;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;

public interface UserDao extends IDatabaseAccessObject<User> {

    SingleModelResult<User> readUserByName(String userName);

}
