package de.fhws.fiw.fds.sutton.server.database.hibernate;

import de.fhws.fiw.fds.sutton.server.api.rateLimiting.model.APIKey;
import de.fhws.fiw.fds.sutton.server.api.rateLimiting.operation.PersistAPIKeyOperation;
import de.fhws.fiw.fds.sutton.server.api.rateLimiting.operation.ReadAllAPIKeysOperation;
import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DatabaseInstaller implements IDatabaseConnection {

    public void install() {
        initializeAPIKeys();
    }

    // TODO change with when Authorization with Users is Implemented
    private void initializeAPIKeys() {
        List<APIKey> apiKeys = new ArrayList<>();
        apiKeys.add(new APIKey("API_KEY_01", 10,10));
        apiKeys.add(new APIKey("API_KEY_02", 10,10));
        apiKeys.add(new APIKey("API_KEY_03", 10,10));
        apiKeys.add(new APIKey("API_KEY_04", 10,10));
        apiKeys.add(new APIKey("API_KEY_05", 10,10));
        apiKeys.forEach(apiKey -> {
            new PersistAPIKeyOperation(SUTTON_EMF, apiKey).start();
        });
        System.out.println("Installed API-Keys.");

        Collection<APIKey> apiKeysOnDB = new ReadAllAPIKeysOperation(SUTTON_EMF, SearchParameter.DEFAULT).start().getResult();
        apiKeysOnDB.forEach(apiKey -> {
            System.out.println("Found API-Key on DB: " + apiKey.toString());
        });
    }

}
