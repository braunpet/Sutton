package de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.database.operations;

import de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.database.models.BinaryDataDBModel;
import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.model.AbstractDeleteAllOperation;
import jakarta.persistence.EntityManagerFactory;

public class DeleteAllBinaryDataOperation extends AbstractDeleteAllOperation<BinaryDataDBModel> {

    public DeleteAllBinaryDataOperation(EntityManagerFactory emf) {
        super(emf, BinaryDataDBModel.class);
    }

}
