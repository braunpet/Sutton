package de.fhws.fiw.fds.sutton.server.utils;

import com.owlike.genson.Context;
import com.owlike.genson.Converter;
import com.owlike.genson.stream.ObjectReader;
import com.owlike.genson.stream.ObjectWriter;

import java.util.Base64;

/**
 * The JsonByteArrayConverter class provides the required functionality to serialize and deserialize a byte array
 * object to and from the JSON format using Base64 encoding and decoding.
 */
public class JsonByteArrayConverter implements Converter<byte[]> {

    @Override
    public void serialize(byte[] bytes, ObjectWriter objectWriter, Context context) throws Exception {
        String encoded = Base64.getEncoder().encodeToString(bytes);
        objectWriter.writeString(encoded);
    }

    @Override
    public byte[] deserialize(ObjectReader objectReader, Context context) throws Exception {
        String encoded = objectReader.valueAsString();
        return Base64.getDecoder().decode(encoded);
    }
}
