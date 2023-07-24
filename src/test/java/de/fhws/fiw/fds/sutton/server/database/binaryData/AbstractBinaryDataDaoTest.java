package de.fhws.fiw.fds.sutton.server.database.binaryData;

import de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.database.dao.BinaryDataDaoAdapter;
import de.fhws.fiw.fds.sutton.server.database.binaryData.database.BinaryDataResourceHandler;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.models.BinaryDataModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import javax.ws.rs.core.MediaType;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public abstract class AbstractBinaryDataDaoTest {

    protected BinaryDataResourceHandler resourceHandler;
    protected BinaryDataDaoAdapter binaryDataDao;
    protected BinaryDataModel testModel;
    protected BinaryDataResourceHandler mockHandler;

    @BeforeEach
    void setUp() throws IOException {
        resourceHandler = new BinaryDataResourceHandler();
        resourceHandler.deleteAllBinaryData(); // delete all files in the src/main/resources/binaryData/ folder before start testing

        binaryDataDao = new BinaryDataDaoAdapter();
        mockHandler = Mockito.mock(BinaryDataResourceHandler.class);

        byte[] testData = {1, 2, 3, 4, 5};
        testModel = new BinaryDataModel(testData, MediaType.APPLICATION_OCTET_STREAM_TYPE.toString());

        NoContentResult createResult = binaryDataDao.create(testModel);
        assertFalse(createResult.hasError());
        SingleModelResult<BinaryDataModel> createReadResult = binaryDataDao.readById(testModel.getId());
        assertArrayEquals(testModel.getData(), createReadResult.getResult().getData());
    }

    @AfterEach
    void tearDown() {
        binaryDataDao.deleteAll();
    }

}
