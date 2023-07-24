package de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.database.dao;

import de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.database.operations.LoadAllBinaryDataByMediaTypeOperation;
import de.fhws.fiw.fds.sutton.server.database.binaryData.database.BinaryDataResourceHandler;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.CollectionModelHibernateResult;
import de.fhws.fiw.fds.sutton.server.database.hibernate.results.SingleModelHibernateResult;
import de.fhws.fiw.fds.sutton.server.database.results.AbstractResult;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.models.BinaryDataModel;
import de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.database.models.BinaryDataDBModel;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Supplier;

/**
 * The BinaryDataDaoAdapter class provides an implementation of the BinaryDataDao interface.
 * It provides methods to handle binary data in the local FileSystem and in the database.
 */
public class BinaryDataDaoAdapter implements BinaryDataDao {

    /**
     * The resource handler used to manage binary data resources.
     */
    private BinaryDataResourceHandler resourceHandler;

    /**
     * The BinaryDataDaoHibernateImpl used to manage binary data in the database.
     */
    private final BinaryDataDaoHibernate dao;

    /**
     * Constructs a new BinaryDataDaoAdapter with a new BinaryDataResourceHandler and BinaryDataDaoHibernateImpl.
     */
    public BinaryDataDaoAdapter() {
        this.resourceHandler = new BinaryDataResourceHandler();
        this.dao = new BinaryDataDaoHibernateImpl();
    }

    /**
     * Handles IOExceptions that may occur during database operations.
     *
     * @param e the IOException
     * @param message the error message
     * @param resultBuilder the result builder
     * @return a result with the error information
     */
    private <T extends AbstractResult> T handleIOException(IOException e, String message, Supplier<T> resultBuilder) {
        T result = resultBuilder.get();
        result.setError(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), message + " " + e.getMessage());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NoContentResult create(BinaryDataModel model) {
        BinaryDataDBModel dbModel = createFrom(model);
        NoContentResult result = dao.create(dbModel);
        if (result.hasError()) {
            return result;
        }
        model.setId(dbModel.getId());
        // updateFileReference again, because model.getId() was 0 previously
        dbModel.setDataFileReference(BinaryDataResourceHandler.RESOURCE_DIRECTORY + dbModel.getId());
        dao.update(dbModel);
        try {
            resourceHandler.saveBinaryData(dbModel.getId(), model.getData());
        } catch (IOException e) {
            return handleIOException(e, "Error persisting binary data file on FileSystem.", NoContentResult::new);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SingleModelResult<BinaryDataModel> readById(long id) {
        return buildSingleModelResult(dao.readById(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CollectionModelResult<BinaryDataModel> readAll() {
        return buildCollectionModelResult(dao.readAll());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NoContentResult update(BinaryDataModel model) {
        BinaryDataDBModel dbModel = createFrom(model);
        NoContentResult result = dao.update(dbModel);
        if (result.hasError()) {
            return result;
        }
        try {
            resourceHandler.updateBinaryData(dbModel.getId(), model.getData());
        } catch (IOException e) {
            return handleIOException(e, "Error updating binary data file.", NoContentResult::new);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NoContentResult delete(long id) {
        NoContentResult result = dao.delete(id);
        if (result.hasError()) {
            return result;
        }
        try {
            resourceHandler.deleteBinaryData(id);
        } catch (IOException e) {
            return handleIOException(e, "Error deleting binary data file on FileSystem.", NoContentResult::new);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NoContentResult deleteAll() {
        NoContentResult result = dao.deleteAll();
        if (result.hasError()) {
            return result;
        }
        try {
            resourceHandler.deleteAllBinaryData();
        } catch (IOException e) {
            return handleIOException(e, "Error deleting all binary data files on FileSystem.", NoContentResult::new);
        }
        return result;
    }

    /**
     * Reads all binary data of a specific media type from the database.
     *
     * @param mediaType the media type of the binary data to read
     * @return a CollectionModelResult containing all binary data of the specified media type
     */
    @Override
    public CollectionModelResult<BinaryDataModel> readAllByMediaType(String mediaType) {
        return buildCollectionModelResult(dao.readAllByMediaType(mediaType));
    }

    /**
     * Deletes all binary data of a specific media type from the database.
     *
     * @param mediaType the media type of the binary data to delete
     * @return a NoContentResult indicating the result of the operation
     */
    @Override
    public NoContentResult deleteAllByMediaType(String mediaType) {
        CollectionModelResult<BinaryDataModel> allByMediaType = readAllByMediaType(mediaType);
        if (allByMediaType.hasError()) {
            return new NoContentResult.NoContentResultBuilder()
                    .setError(allByMediaType.getErrorCode(), allByMediaType.getErrorMessage())
                    .build();
        }
        for (BinaryDataModel model : allByMediaType.getResult()) {
            NoContentResult deleteResult = delete(model.getId());
            if (deleteResult.hasError()) {
                return deleteResult;
            }
        }
        return new NoContentResult.NoContentResultBuilder().build();
    }

    /**
     * Sets the resource handler. This method is only for testing with Mockito.
     *
     * @param resourceHandler the resource handler to set
     */
    public void setResourceHandler(BinaryDataResourceHandler resourceHandler) {
        this.resourceHandler = resourceHandler;
    }

    /**
     * Converts a BinaryDataModel to a BinaryDataDBModel.
     *
     * @param model the BinaryDataModel to convert
     * @return the converted BinaryDataDBModel
     */
    private BinaryDataDBModel createFrom(BinaryDataModel model) {
        BinaryDataDBModel dbModel = new BinaryDataDBModel();
        dbModel.setId(model.getId());
        dbModel.setDataFileReference(BinaryDataResourceHandler.RESOURCE_DIRECTORY + model.getId());
        dbModel.setMediaType(model.getMediaType().toString());
        return dbModel;
    }

    /**
     * Converts a BinaryDataDBModel to a BinaryDataModel.
     *
     * @param dbModel the BinaryDataDBModel to convert
     * @return the converted BinaryDataModel or null if an error occurred
     */
    private BinaryDataModel createFrom(BinaryDataDBModel dbModel) {
        BinaryDataModel model = new BinaryDataModel();
        model.setId(dbModel.getId());
        model.setMediaType(dbModel.getMediaType());
        try {
            model.setData(Files.readAllBytes(Paths.get(dbModel.getDataFileReference())));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return model;
    }

    /**
     * Converts a collection of BinaryDataDBModels to a collection of BinaryDataModels.
     *
     * @param dbModels the collection of BinaryDataDBModels to convert
     * @return the converted collection of BinaryDataModels
     */
    private Collection<BinaryDataModel> createFrom(Collection<BinaryDataDBModel> dbModels) {
        Collection<BinaryDataModel> models = new LinkedList<>();
        for (BinaryDataDBModel dbModel : dbModels) {
            BinaryDataModel model = createFrom(dbModel);
            if (model != null) {
                models.add(model);
            }
        }
        return models;
    }

    /**
     * Builds a SingleModelResult from a SingleModelHibernateResult.
     *
     * @param dbResult the SingleModelHibernateResult to convert
     * @return the built SingleModelResult
     */
    private SingleModelResult<BinaryDataModel> buildSingleModelResult(SingleModelHibernateResult<BinaryDataDBModel> dbResult) {
        if (dbResult.hasError()) {
            return new SingleModelResult.SingleModelResultBuilder<BinaryDataModel>()
                    .setError(dbResult.getErrorCode(), dbResult.getErrorMessage())
                    .build();
        }
        BinaryDataModel model = createFrom(dbResult.getResult());
        if (model == null) {
            return new SingleModelResult.SingleModelResultBuilder<BinaryDataModel>()
                    .setError(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "Error reading binary data file")
                    .build();
        }
        return new SingleModelResult.SingleModelResultBuilder<BinaryDataModel>()
                .setResult(model)
                .build();
    }

    /**
     * Builds a CollectionModelResult from a CollectionModelHibernateResult.
     *
     * @param dbResult the CollectionModelHibernateResult to convert
     * @return the built CollectionModelResult
     */
    private CollectionModelResult<BinaryDataModel> buildCollectionModelResult(CollectionModelHibernateResult<BinaryDataDBModel> dbResult) {
        if (dbResult.hasError()) {
            return new CollectionModelResult.CollectionModelResultBuilder<BinaryDataModel>()
                    .setError(dbResult.getErrorCode(), dbResult.getErrorMessage())
                    .build();
        }
        Collection<BinaryDataModel> models = createFrom(dbResult.getResult());
        return new CollectionModelResult.CollectionModelResultBuilder<BinaryDataModel>()
                .setResult(models)
                .setTotalNumberOfResult(models.size())
                .build();
    }
}

