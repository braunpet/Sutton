package de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.database.dao;

import de.fhws.fiw.fds.sutton.server.database.IDatabaseAccessObject;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.searchParameter.SearchParameter;
import de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.models.BinaryDataModel;

/**
 * The BinaryDataDao interface provides methods to handle binary data on the local FileSystem.
 * It extends the IDatabaseAccessObject interface with methods specific to binary data.
 */
public interface BinaryDataDao extends IDatabaseAccessObject<BinaryDataModel> {

    /**
     * Retrieves all binary data that match the given search parameter.
     * This method is overridden from the IDatabaseAccessObject interface, because we don't need a {@link SearchParameter}
     * for the local FileSystem.
     *
     * @param searchParameter the search parameter to match the binary data
     * @return a CollectionModelResult containing the matching binary data
     */
    @Override
    default CollectionModelResult<BinaryDataModel> readAll(SearchParameter searchParameter) {
        return readAll();
    }

    /**
     * Retrieves all binary data.
     *
     * @return a CollectionModelResult containing all binary data
     */
    CollectionModelResult<BinaryDataModel> readAll();

    /**
     * Deletes all binary data.
     *
     * @return a NoContentResult indicating the result of the operation
     */
    NoContentResult deleteAll();
}
