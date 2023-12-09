package de.fhws.fiw.fds.suttondemoHibernate;

import de.fhws.fiw.fds.sutton.server.AbstractDatabaseInstaller;
import de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.models.ApiKeyDB;
import de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.operation.PersistApiKeyOperation;
import de.fhws.fiw.fds.sutton.server.api.security.database.models.UserDB;
import de.fhws.fiw.fds.sutton.server.api.security.database.operations.user.PersistUserOperation;
import de.fhws.fiw.fds.sutton.server.api.security.database.operations.user_role.UpdateUserRoleOperation;
import de.fhws.fiw.fds.sutton.server.api.security.helper.SecretHashingHelper;

import java.util.ArrayList;
import java.util.List;

public class InstallerForDevelopment extends AbstractDatabaseInstaller {

    @Override
    protected void installUsers() {
        initializeUsers();
    }

    @Override
    protected void installApiKeys() {
        initializeAPIKeys();
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

        new UpdateUserRoleOperation(SUTTON_EMF, userPeter.getId(), super.adminRole).start();
        new UpdateUserRoleOperation(SUTTON_EMF, userNarm0X.getId(), super.modRole).start();
        System.out.println("Set Roles for Users.");
    }

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

}
