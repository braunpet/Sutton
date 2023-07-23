package de.fhws.fiw.fds.sutton.server.database.binaryData;

import de.fhws.fiw.fds.sutton.server.database.binbaryData.BinaryDataDaoImpl;
import de.fhws.fiw.fds.sutton.server.database.binbaryData.BinaryDataResourceHandler;
import de.fhws.fiw.fds.sutton.server.database.results.NoContentResult;
import de.fhws.fiw.fds.sutton.server.database.results.SingleModelResult;
import de.fhws.fiw.fds.sutton.server.models.BinaryDataModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public abstract class AbstractBinaryDataDaoTest {

    protected BinaryDataDaoImpl binaryDataDao;
    protected BinaryDataModel testModel;
    protected BinaryDataResourceHandler mockHandler;

    @BeforeEach
    void setUp() {
        binaryDataDao = new BinaryDataDaoImpl();
        mockHandler = Mockito.mock(BinaryDataResourceHandler.class);

        byte[] testData = {1, 2, 3, 4, 5};
        testModel = new BinaryDataModel(testData);
        testModel.setId(1);

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
