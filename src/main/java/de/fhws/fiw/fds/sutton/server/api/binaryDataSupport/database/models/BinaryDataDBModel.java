package de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.database.models;

import de.fhws.fiw.fds.sutton.server.database.hibernate.models.AbstractDBModel;
import jakarta.persistence.Entity;

/**
 * The BinaryDataDBModel class extends the AbstractDBModel class and represents a model for binary data.
 * It contains a string to hold the reference to the binary data file.
 */
@Entity
public class BinaryDataDBModel extends AbstractDBModel {

    /**
     * The string to hold the reference to the binary data file.
     */
    private String dataFileReference;

    /**
     * Empty Constructor for serialisation and Hibernate
     */
    public BinaryDataDBModel() {
        //make JPA happy
    }

    /**
     * Constructs a new BinaryDataDBModel with the specified file reference.
     *
     * @param dataFileReference the file reference to be held by this model
     */
    public BinaryDataDBModel(String dataFileReference) {
        this.dataFileReference = dataFileReference;
    }

    public String getDataFileReference() {
        return dataFileReference;
    }

    public void setDataFileReference(String dataFileReference) {
        this.dataFileReference = dataFileReference;
    }
}
