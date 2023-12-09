package de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.database.operations;

import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.model.AbstractUpdateOperation;
import de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.database.models.BinaryDataDBModel;
import jakarta.persistence.EntityManagerFactory;

public class UpdateBinaryDataOperation extends AbstractUpdateOperation<BinaryDataDBModel> {

    public UpdateBinaryDataOperation(EntityManagerFactory emf, BinaryDataDBModel modelToUpdate) {
        super(emf, modelToUpdate);
    }

}
