package de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.models;

import com.owlike.genson.annotation.JsonConverter;
import de.fhws.fiw.fds.sutton.server.api.converter.JsonServerLinkConverter;
import de.fhws.fiw.fds.sutton.server.api.converter.XmlServerLinkConverter;
import de.fhws.fiw.fds.sutton.server.models.AbstractModel;
import de.fhws.fiw.fds.sutton.server.utils.JsonByteArrayConverter;
import de.fhws.fiw.fds.sutton.server.utils.XmlByteArrayConverter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.glassfish.jersey.linking.InjectLink;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;

/**
 * The BinaryDataModel class extends the AbstractModel class and represents a model for binary data.
 * It contains a byte array to hold the binary data.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class BinaryDataModel extends AbstractModel {

    /**
     * The byte array to hold the binary data.
     */
    @XmlJavaTypeAdapter(XmlByteArrayConverter.class)
    private byte[] data;

    /**
     * The media type of the data.
     */
    private String mediaType;

    @InjectLink(
            title = "self",
            value = "/binaryData/${instance.id}",
            rel = "self",
            style = InjectLink.Style.ABSOLUTE,
            type = "application/octet-stream")
    @XmlJavaTypeAdapter( XmlServerLinkConverter.class )
    private Link selfLink;

    /**
     * Empty Constructor for serialisation
     */
    public BinaryDataModel() {

    }

    /**
     * Constructs a new BinaryDataModel with the specified binary data and media type.
     *
     * @param data the binary data to be held by this model
     * @param mediaType the media type of the data
     */
    public BinaryDataModel(byte[] data, String mediaType) {
        this.data = data;
        this.mediaType = mediaType;
    }

    @JsonConverter(JsonByteArrayConverter.class)
    public byte[] getData() {
        return data;
    }


    @JsonConverter(JsonByteArrayConverter.class)
    public void setData(byte[] data) {
        this.data = data;
    }


    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    @JsonConverter(JsonServerLinkConverter.class)
    public Link getSelfLink() {
        return selfLink;
    }

    @JsonConverter(JsonServerLinkConverter.class)
    public void setSelfLink(Link selfLink) {
        this.selfLink = selfLink;
    }
}
