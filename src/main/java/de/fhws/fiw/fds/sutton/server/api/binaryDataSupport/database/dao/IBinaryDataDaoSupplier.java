package de.fhws.fiw.fds.sutton.server.database.binaryData.database.dao;

/**
 * The IBinaryDataDaoSupplier interface provides a method to get an instance of BinaryDataDaoImpl.
 */
public interface IBinaryDataDaoSupplier {

    /**
     * Returns an instance of BinaryDataDaoImpl.
     *
     * @return an instance of BinaryDataDaoImpl
     */
    default de.fhws.fiw.fds.sutton.server.database.binaryData.database.dao.BinaryDataDao getBinaryDataDao() {
        return new de.fhws.fiw.fds.sutton.server.database.binaryData.database.dao.BinaryDataDaoImpl();
    }

}
