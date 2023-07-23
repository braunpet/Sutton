package de.fhws.fiw.fds.sutton.server.models;

/**
 * The BinaryDataModel class extends the AbstractModel class and represents a model for binary data.
 * It contains a byte array to hold the binary data.
 */
public class BinaryDataModel extends AbstractModel {

    /**
     * The byte array to hold the binary data.
     */
    private byte[] data;

    /**
     * Constructs a new BinaryDataModel with the specified binary data.
     *
     * @param data the binary data to be held by this model
     */
    public BinaryDataModel(byte[] data) {
        this.data = data;
    }

    /**
     * Returns the binary data held by this model.
     *
     * @return the binary data held by this model
     */
    public byte[] getData() {
        return data;
    }

    /**
     * Sets the binary data to be held by this model.
     *
     * @param data the binary data to be held by this model
     */
    public void setData(byte[] data) {
        this.data = data;
    }

}
