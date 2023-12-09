package de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.database.dao;

import de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.database.models.BinaryDataDBModel;
import de.fhws.fiw.fds.sutton.server.database.hibernate.dao.IDatabaseAccessObjectHibernate;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.CollectionModelHibernateResult;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;

public interface BinaryDataDaoHibernate extends IDatabaseAccessObjectHibernate<BinaryDataDBModel> {

    @Override
    default CollectionModelHibernateResult<BinaryDataDBModel> readAll(SearchParameter searchParameter) {
        return readAll();
    }

    CollectionModelHibernateResult<BinaryDataDBModel> readAll();

    NoContentResult deleteAll();

    CollectionModelHibernateResult<BinaryDataDBModel> readAllByMediaType(String mediaType);

    NoContentResult deleteAllByMediaType(String mediaType);
}
