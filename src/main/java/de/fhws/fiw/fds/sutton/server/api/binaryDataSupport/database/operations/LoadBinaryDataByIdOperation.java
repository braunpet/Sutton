package de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.database.operations;

import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.model.AbstractReadByIdOperation;
import de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.database.models.BinaryDataDBModel;
import jakarta.persistence.EntityManagerFactory;

public class LoadBinaryDataByIdOperation extends AbstractReadByIdOperation<BinaryDataDBModel> {

    public LoadBinaryDataByIdOperation(EntityManagerFactory emf, long idToLoad) {
        super(emf, BinaryDataDBModel.class, idToLoad);
    }

}
