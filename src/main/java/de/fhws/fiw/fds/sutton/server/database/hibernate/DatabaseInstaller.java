package de.fhws.fiw.fds.sutton.server.database.hibernate;

import de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.models.ApiKeyDB;
import de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.operation.PersistApiKeyOperation;
import de.fhws.fiw.fds.sutton.server.api.security.SecretHashingHelper;
import de.fhws.fiw.fds.sutton.server.api.security.database.models.RoleDB;
import de.fhws.fiw.fds.sutton.server.api.security.database.models.UserDB;
import de.fhws.fiw.fds.sutton.server.api.security.database.operations.role.PersistRoleOperation;
import de.fhws.fiw.fds.sutton.server.api.security.database.operations.user.PersistUserOperation;
import de.fhws.fiw.fds.sutton.server.api.security.database.operations.user_role.UpdateUserRoleOperation;

import java.util.ArrayList;
import java.util.List;

import static de.fhws.fiw.fds.sutton.server.database.hibernate.DatabaseInstaller.RoleNames.*;

public class DatabaseInstaller implements IDatabaseConnection {

    public void install() {
        initializeAPIKeys();
        initializeRoles();
        initializeUsers();
    }

    // TODO change with when Authorization with Users is Implemented
    private void initializeAPIKeys() {
        List<ApiKeyDB> apiKeyDBS = new ArrayList<>();
        apiKeyDBS.add(new ApiKeyDB("API_KEY_01", 10, 10));
        apiKeyDBS.add(new ApiKeyDB("API_KEY_02", 10, 10));
        apiKeyDBS.add(new ApiKeyDB("API_KEY_03", 10, 10));
        apiKeyDBS.add(new ApiKeyDB("API_KEY_04", 10, 10));
        apiKeyDBS.add(new ApiKeyDB("API_KEY_05", 10, 10));
        apiKeyDBS.forEach(apiKey -> new PersistApiKeyOperation(SUTTON_EMF, apiKey).start());
        System.out.println("Installed API-Keys.");
    }

    public static class RoleNames{
        protected static final String ADMIN = "Admin";
        protected static final String MOD = "Moderator";
        protected static final String USER = "User";
        protected static final String GUEST = "Guest";

        public static final List<String> ADMIN_ROLES = List.of(ADMIN);
        public static final List<String> MOD_ROLES = List.of(ADMIN, MOD);
        public static final List<String> USER_ROLES = List.of(ADMIN, MOD, USER);
        public static final List<String> GUEST_ROLES = List.of(ADMIN, MOD, USER, GUEST);
    }

    private RoleDB adminRole = new RoleDB(ADMIN, true, true, true, true);
    private RoleDB modRole = new RoleDB(MOD, true, true, true, true);
    private RoleDB userRole = new RoleDB(USER, false, true, false, false);
    private RoleDB guestRole = new RoleDB(GUEST, false, false, false, false);

    private void initializeRoles() {
        List<RoleDB> roles = new ArrayList<>();
        roles.add(adminRole);
        roles.add(modRole);
        roles.add(userRole);
        roles.add(guestRole);
        roles.forEach(role -> new PersistRoleOperation(SUTTON_EMF, role).start());
        System.out.println("Installed Roles.");
    }

    private final byte[] salt1 = SecretHashingHelper.getSalt();
    private UserDB userPeter = new UserDB("PeterBraun", SecretHashingHelper.hashPassword("&%24msdh3gkj", salt1), SecretHashingHelper.saltToString(salt1));
    private final byte[] salt2 = SecretHashingHelper.getSalt();
    private UserDB userNarm0X = new UserDB("Narm0X", SecretHashingHelper.hashPassword("!2rfG34wwt7g", salt2), SecretHashingHelper.saltToString(salt2));

    private void initializeUsers() {
        List<UserDB> users = new ArrayList<>();
        users.add(userNarm0X);
        users.add(userPeter);
        users.forEach(user -> new PersistUserOperation(SUTTON_EMF, user).start());
        System.out.println("Installed Users.");

        new UpdateUserRoleOperation(SUTTON_EMF, userPeter.getId(), adminRole).start();
        new UpdateUserRoleOperation(SUTTON_EMF, userNarm0X.getId(), modRole).start();
        System.out.println("Set Roles for Users.");
    }
}
