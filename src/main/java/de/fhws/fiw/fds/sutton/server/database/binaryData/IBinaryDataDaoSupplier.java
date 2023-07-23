package de.fhws.fiw.fds.sutton.server.database.binaryData;

/**
 * The IBinaryDataDaoSupplier interface provides a method to get an instance of BinaryDataDaoImpl.
 */
public interface IBinaryDataDaoSupplier {

    /**
     * Returns an instance of BinaryDataDaoImpl.
     *
     * @return an instance of BinaryDataDaoImpl
     */
    default BinaryDataDao getBinaryDataDao() {
        return new BinaryDataDaoImpl();
    }

}
