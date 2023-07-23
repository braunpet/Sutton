package de.fhws.fiw.fds.sutton.server.database.binaryData;

import de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.database.dao.BinaryDataDaoImpl;
import de.fhws.fiw.fds.sutton.server.database.results.CollectionModelResult;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.models.BinaryDataModel;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;

public class BinaryDataDaoTest extends AbstractBinaryDataDaoTest {

    @Test
    void test_create_success() {
        testModel.setId(2);
        NoContentResult result = binaryDataDao.create(testModel);
        testModel.setId(1);
        assertFalse(result.hasError());
        SingleModelResult<BinaryDataModel> readResult = binaryDataDao.readById(testModel.getId());
        assertArrayEquals(testModel.getData(), readResult.getResult().getData());
    }

    @Test
    void testCreateFailure() {
        try {
            doThrow(new IOException("Test exception")).when(mockHandler).saveBinaryData(anyLong(), any(byte[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        BinaryDataDaoImpl dao = new BinaryDataDaoImpl();
        dao.setResourceHandler(mockHandler);
        NoContentResult result = dao.create(testModel);

        assertTrue(result.hasError());
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), result.getErrorCode());
        assertEquals("Error persisting binary data file on FileSystem. Test exception", result.getErrorMessage());
    }

    @Test
    void test_read_by_ID_success() {
        SingleModelResult<BinaryDataModel> result = binaryDataDao.readById(testModel.getId());
        assertFalse(result.hasError());
        assertArrayEquals(testModel.getData(), result.getResult().getData());
    }

    @Test
    void test_read_by_ID_failure() {
        SingleModelResult<BinaryDataModel> result = binaryDataDao.readById(9999L); // not existing ID
        assertTrue(result.hasError());
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), result.getErrorCode());
    }

    @Test
    void test_update_success() {
        byte[] updatedData = {6, 7, 8, 9, 10};
        testModel.setData(updatedData);
        NoContentResult result = binaryDataDao.update(testModel);
        assertFalse(result.hasError());
        SingleModelResult<BinaryDataModel> readResult = binaryDataDao.readById(testModel.getId());
        assertArrayEquals(updatedData, readResult.getResult().getData());
    }

    @Test
    void test_update_not_existing_file() {
        BinaryDataModel nonExistingModel = new BinaryDataModel(new byte[]{11, 12, 13, 14, 15});
        nonExistingModel.setId(9999L);
        NoContentResult result = binaryDataDao.update(nonExistingModel);
        assertTrue(result.hasError());
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), result.getErrorCode());
    }

    @Test
    void test_update_cannot_delete_file() {
        try {
            doThrow(new IOException("Cannot delete file")).when(mockHandler).updateBinaryData(anyLong(), any(byte[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        BinaryDataDaoImpl dao = new BinaryDataDaoImpl();
        dao.setResourceHandler(mockHandler);
        NoContentResult result = dao.update(testModel);

        assertTrue(result.hasError());
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), result.getErrorCode());
        assertEquals("Error updating binary data file. Cannot delete file", result.getErrorMessage());
    }

    @Test
    void test_delete_success() {
        NoContentResult result = binaryDataDao.delete(testModel.getId());
        assertFalse(result.hasError());
        SingleModelResult<BinaryDataModel> readResult = binaryDataDao.readById(testModel.getId());
        assertTrue(readResult.hasError());
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), readResult.getErrorCode());
    }

    @Test
    void test_deleteAll_success() {
        NoContentResult result = binaryDataDao.deleteAll();
        assertFalse(result.hasError());
        CollectionModelResult<BinaryDataModel> readResult = binaryDataDao.readAll();
        assertTrue(readResult.getResult().isEmpty());
    }
}
