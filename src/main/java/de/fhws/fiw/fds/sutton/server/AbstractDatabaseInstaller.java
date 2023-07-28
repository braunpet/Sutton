package de.fhws.fiw.fds.sutton.server;

import de.fhws.fiw.fds.sutton.server.api.security.database.models.RoleDB;
import de.fhws.fiw.fds.sutton.server.api.security.database.operations.role.PersistRoleOperation;

import java.util.ArrayList;
import java.util.List;

import static de.fhws.fiw.fds.sutton.server.AbstractDatabaseInstaller.RoleNames.*;

/**
 * AbstractDatabaseInstaller is an abstract class that provides the basic structure for installing roles, users,
 * and API keys in the database.
 * It implements the {@link IDatabaseConnection} interface to connect to the Database.
 */
public abstract class AbstractDatabaseInstaller implements IDatabaseConnection {

    /**
     * This method is used to install roles, users, and API keys in the database.
     * Run this in the Main-Method of your custom Installer.
     */
    public void install() {
        installRoles();
        installUsers();
        installApiKeys();
    }

    /**
     * RoleNames is a static nested class that defines the role names and their respective permissions.
     */
    public static class RoleNames{
        public static final String ADMIN = "Admin";
        public static final String MOD = "Moderator";
        public static final String USER = "User";
        public static final String GUEST = "Guest";

        public static final List<String> ADMIN_ROLES = List.of(ADMIN);
        public static final List<String> MOD_ROLES = List.of(ADMIN, MOD);
        public static final List<String> USER_ROLES = List.of(ADMIN, MOD, USER);
        public static final List<String> GUEST_ROLES = List.of(ADMIN, MOD, USER, GUEST);
    }

    /**
     * The default administrator {@link RoleDB}
     */
    protected RoleDB adminRole = new RoleDB(ADMIN, true, true, true, true);

    /**
     * The default moderator {@link RoleDB}
     */
    protected RoleDB modRole = new RoleDB(MOD, true, true, true, true);

    /**
     * The default user {@link RoleDB}
     */
    protected RoleDB userRole = new RoleDB(USER, false, true, false, false);

    /**
     * The default guest {@link RoleDB}
     */
    protected RoleDB guestRole = new RoleDB(GUEST, false, false, false, false);

    /**
     * This method is used to install {@link RoleDB}s in the database.
     * It creates a list of roles and adds them to the database using the {@link PersistRoleOperation} class.
     */
    private void installRoles() {
        List<RoleDB> roles = new ArrayList<>();
        roles.add(adminRole);
        roles.add(modRole);
        roles.add(userRole);
        roles.add(guestRole);
        roles.forEach(role -> new PersistRoleOperation(SUTTON_EMF, role).start());
        System.out.println("Installed Roles.");
    }

    /**
     * This method is an abstract method that needs to be implemented by the subclasses to install users in the database.
     */
    protected abstract void installUsers();

    /**
     * This method is an abstract method that needs to be implemented by the subclasses to install API keys in the database.
     */
    protected abstract void installApiKeys();

}
