package de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.database.dao;

import de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.database.models.BinaryDataDBModel;
import de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.database.operations.*;
import de.fhws.fiw.fds.sutton.server.database.hibernate.IDatabaseConnection;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.CollectionModelHibernateResult;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.SingleModelHibernateResult;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import jakarta.persistence.EntityManagerFactory;

public class BinaryDataDaoHibernateImpl implements BinaryDataDaoHibernate {

    private static final EntityManagerFactory emf = IDatabaseConnection.SUTTON_EMF;

    public BinaryDataDaoHibernateImpl() {
        super();
    }

    @Override
    public NoContentResult create(BinaryDataDBModel model) {
        return new PersistBinaryDataOperation(emf, model).start();
    }

    @Override
    public SingleModelHibernateResult<BinaryDataDBModel> readById(long id) {
        return new LoadBinaryDataByIdOperation(emf, id).start();
    }

    @Override
    public CollectionModelHibernateResult<BinaryDataDBModel> readAll() {
        return new LoadAllBinaryDataOperation(emf).start();
    }

    @Override
    public NoContentResult update(BinaryDataDBModel model) {
        return new UpdateBinaryDataOperation(emf, model).start();
    }

    @Override
    public NoContentResult delete(long id) {
        return new DeleteBinaryDataByIdOperation(emf, id).start();
    }

    @Override
    public NoContentResult deleteAll() {
        return new DeleteAllBinaryDataOperation(emf).start();
    }

    @Override
    public CollectionModelHibernateResult<BinaryDataDBModel> readAllByMediaType(String mediaType) {
        return new LoadAllBinaryDataByMediaTypeOperation(emf, mediaType).start();
    }

    @Override
    public NoContentResult deleteAllByMediaType(String mediaType) {
        return new DeleteAllBinaryDataByMediaTypeOperation(emf, mediaType).start();
    }
}
