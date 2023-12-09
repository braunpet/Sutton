package de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.database.operations;

import de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.database.models.BinaryDataDBModel;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.model.AbstractDeleteByIdOperation;
import jakarta.persistence.EntityManagerFactory;

public class DeleteBinaryDataByIdOperation extends AbstractDeleteByIdOperation<BinaryDataDBModel> {

    public DeleteBinaryDataByIdOperation(EntityManagerFactory emf, long idToDelete) {
        super(emf, BinaryDataDBModel.class, idToDelete);
    }

}
