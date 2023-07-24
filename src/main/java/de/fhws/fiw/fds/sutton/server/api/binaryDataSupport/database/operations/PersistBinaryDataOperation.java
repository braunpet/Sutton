package de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.database.operations;

import de.fhws.fiw.fds.sutton.server.database.hibernate.operations.model.AbstractPersistOperation;
import de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.database.models.BinaryDataDBModel;
import jakarta.persistence.EntityManagerFactory;

public class PersistBinaryDataOperation extends AbstractPersistOperation<BinaryDataDBModel> {

    public PersistBinaryDataOperation(EntityManagerFactory emf, BinaryDataDBModel modelToPersist) {
        super(emf, modelToPersist);
    }

}
