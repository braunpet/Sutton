package de.fhws.fiw.fds.sutton.server.api.security.database.dao;

/**
 * Each method in this interface returns a new instance of the respective DaoAdapter, which can be used to perform
 * database operations related to the respective entity (Role, User, UserRole).
 */
public interface IAuthDaoSupplier {

    /**
     * Returns an instance of RoleDaoAdapter.
     *
     * @return an instance of RoleDaoAdapter
     */
    default RoleDao getRoleDao() {
        return new RoleDaoAdapter();
    }

    /**
     * Returns an instance of UserDaoAdapter.
     *
     * @return an instance of UserDaoAdapter
     */
    default UserDao getUserDao() {
        return new UserDaoAdapter();
    }

    /**
     * Returns an instance of UserRoleDaoAdapter.
     *
     * @return an instance of UserRoleDaoAdapter
     */
    default UserRoleDao getUserRoleDao() {
        return new UserRoleDaoAdapter();
    }

}
