package de.fhws.fiw.fds.sutton.server.utils;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.util.Base64;

public class XmlByteArrayConverter extends XmlAdapter<String, byte[]> {

    @Override
    public byte[] unmarshal(final String v) throws Exception {
        return Base64.getDecoder().decode(v);
    }

    @Override
    public String marshal(final byte[] v) throws Exception {
        return Base64.getEncoder().encodeToString(v);
    }

}
