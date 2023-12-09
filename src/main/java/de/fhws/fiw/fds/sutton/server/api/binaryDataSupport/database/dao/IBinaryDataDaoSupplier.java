package de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.database.dao;

/**
 * The IBinaryDataDaoSupplier interface provides a method to get an instance of BinaryDataDaoAdapter.
 */
public interface IBinaryDataDaoSupplier {

    /**
     * Returns an instance of BinaryDataDaoAdapter.
     *
     * @return an instance of BinaryDataDaoAdapter
     */
    default BinaryDataDao getBinaryDataDao() {
        return new BinaryDataDaoAdapter();
    }

}
