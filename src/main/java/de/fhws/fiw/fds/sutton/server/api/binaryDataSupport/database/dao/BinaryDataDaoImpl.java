package de.fhws.fiw.fds.sutton.server.database.binaryData.database.dao;

import de.fhws.fiw.fds.sutton.server.database.binaryData.database.BinaryDataResourceHandler;
import de.fhws.fiw.fds.sutton.server.database.results.AbstractResult;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.models.BinaryDataModel;

import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

/**
 * The BinaryDataDaoImpl class provides an implementation of the BinaryDataDao interface.
 * It provides methods to handle binary data in the local FileSystem.
 */
public class BinaryDataDaoImpl implements de.fhws.fiw.fds.sutton.server.database.binaryData.database.dao.BinaryDataDao {

    /**
     * The resource handler used to manage binary data resources.
     */
    private BinaryDataResourceHandler resourceHandler;

    /**
     * Constructs a new BinaryDataDaoImpl with a new BinaryDataResourceHandler.
     */
    public BinaryDataDaoImpl() {
        this.resourceHandler = new BinaryDataResourceHandler();
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
        try {
            resourceHandler.saveBinaryData(model.getId(), model.getData());
            return new NoContentResult();
        } catch (IOException e) {
            return handleIOException(e, "Error persisting binary data file on FileSystem.", NoContentResult::new);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SingleModelResult<BinaryDataModel> readById(long id) {
        File file = resourceHandler.getBinaryData(id);
        if (!file.exists()) {
            return new SingleModelResult.SingleModelResultBuilder<BinaryDataModel>()
                    .setError(Response.Status.NOT_FOUND.getStatusCode(), "Binary data file not found.")
                    .build();
        }

        try {
            byte[] data = Files.readAllBytes(file.toPath());
            BinaryDataModel model = new BinaryDataModel(data);
            model.setId(id);
            return new SingleModelResult.SingleModelResultBuilder<BinaryDataModel>()
                    .setResult(model)
                    .build();
        } catch (IOException e) {
            return handleIOException(e, "Error reading binary data file: ", SingleModelResult::new);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CollectionModelResult<BinaryDataModel> readAll() {
        Collection<BinaryDataModel> models = new LinkedList<>();
        List<File> allBinaryDataFiles = resourceHandler.getAllBinaryData();

        for (File file : allBinaryDataFiles) {
            try {
                byte[] data = Files.readAllBytes(file.toPath());
                BinaryDataModel model = new BinaryDataModel(data);
                model.setId(Long.parseLong(file.getName()));
                models.add(model);
            } catch (IOException e) {
                return handleIOException(e, "Error reading binary data file.", CollectionModelResult::new);
            }
        }

        return new CollectionModelResult.CollectionModelResultBuilder<BinaryDataModel>()
                .setResult(models)
                .setTotalNumberOfResult(models.size())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NoContentResult update(BinaryDataModel model) {
        try {
            resourceHandler.updateBinaryData(model.getId(), model.getData());
            return new NoContentResult();
        } catch (IOException e) {
            return handleIOException(e, "Error updating binary data file.", NoContentResult::new);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NoContentResult delete(long id) {
        try {
            resourceHandler.deleteBinaryData(id);
            return new NoContentResult();
        } catch (IOException e) {
            return handleIOException(e, "Error deleting binary data file on FileSystem.", NoContentResult::new);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NoContentResult deleteAll() {
        try {
            resourceHandler.deleteAllBinaryData();
            return new NoContentResult();
        } catch (IOException e) {
            return handleIOException(e, "Error deleting all binary data files on FileSystem.", NoContentResult::new);
        }
    }

    /**
     * Sets the resource handler. This method is only for testing with Mockito.
     *
     * @param resourceHandler the resource handler to set
     */
    public void setResourceHandler(BinaryDataResourceHandler resourceHandler) {
        this.resourceHandler = resourceHandler;
    }
}
