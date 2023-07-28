package de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.dao;

import de.fhws.fiw.fds.sutton.server.api.rateLimiting.database.models.ApiKeyDB;
import de.fhws.fiw.fds.sutton.server.database.hibernate.dao.IDatabaseAccessObjectHibernate;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.SingleModelHibernateResult;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;

public interface ApiKeyDaoHibernate extends IDatabaseAccessObjectHibernate<ApiKeyDB> {

    SingleModelHibernateResult<ApiKeyDB> readApiKey(String apiKey);

    NoContentResult deleteApiKey(String apiKey);

}
