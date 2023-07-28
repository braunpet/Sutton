package de.fhws.fiw.fds.sutton.advancedClient;

import com.owlike.genson.Genson;
import de.fhws.fiw.fds.sutton.server.api.binaryDataSupport.models.BinaryDataModel;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Map;

public class SuttonAdvancedClient {

    private final HttpClient httpClient;
    private final String baseUrl;
    private final Genson genson;

    public SuttonAdvancedClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.httpClient = HttpClient.newHttpClient();
        this.genson = new Genson();
    }

    public HttpResponse<String> sendGet(String path, Map<String, String> headers) throws Exception {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + path))
                .GET();

        headers.forEach(requestBuilder::header);

        HttpRequest request = requestBuilder.build();

        return httpClient.send(request, BodyHandlers.ofString());
    }

    public HttpResponse<String> sendPost(String path, String json, Map<String, String> headers) throws Exception {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + path))
                .POST(HttpRequest.BodyPublishers.ofString(json));

        headers.forEach(requestBuilder::header);

        HttpRequest request = requestBuilder.build();

        return httpClient.send(request, BodyHandlers.ofString());
    }

    public HttpResponse<String> sendPut(String path, String json, Map<String, String> headers) throws Exception {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + path))
                .PUT(HttpRequest.BodyPublishers.ofString(json));

        headers.forEach(requestBuilder::header);

        HttpRequest request = requestBuilder.build();

        return httpClient.send(request, BodyHandlers.ofString());
    }

    public HttpResponse<String> sendDelete(String path, Map<String, String> headers) throws Exception {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + path))
                .DELETE();

        headers.forEach(requestBuilder::header);

        HttpRequest request = requestBuilder.build();

        return httpClient.send(request, BodyHandlers.ofString());
    }

    public HttpResponse<String> createBinaryData(BinaryDataModel data, Map<String, String> headers) throws Exception {
        String json = genson.serialize(data);
        return sendPost("/binaryData", json, headers);
    }

    public HttpResponse<String> updateBinaryData(String id, BinaryDataModel data, Map<String, String> headers) throws Exception {
        String json = genson.serialize(data);
        return sendPut("/binaryData/" + id, json, headers);
    }

    public BinaryDataModel readBinaryData(String id, Map<String, String> headers) throws Exception {
        HttpResponse<String> response = sendGet("/binaryData/" + id, headers);
        return genson.deserialize(response.body(), BinaryDataModel.class);
    }

    public void deleteBinaryData(String id, Map<String, String> headers) throws Exception {
        sendDelete("/binaryData/" + id, headers);
    }

}
