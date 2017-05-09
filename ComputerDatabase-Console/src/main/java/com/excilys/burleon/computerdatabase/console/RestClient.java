package com.excilys.burleon.computerdatabase.console;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

import com.excilys.burleon.computerdatabase.console.exception.RestClientException;
import com.excilys.burleon.computerdatabase.console.model.Computer;

public class RestClient {

    private final static Client client = ClientBuilder.newClient(new ClientConfig().register(RestClient.class));
    private final static String API_URL = "http://localhost:8080/ComputerDatabase/api/v1";

    public static Optional<Computer> createComputer(final Computer computer) {
        final WebTarget webTarget = RestClient.client.target(RestClient.API_URL).path("computer");
        try {
            return Optional.ofNullable(webTarget.request(MediaType.APPLICATION_JSON_TYPE)
                    .post(Entity.entity(computer, MediaType.APPLICATION_JSON_TYPE)).readEntity(Computer.class));
        } catch (final Exception e) {
            throw new RestClientException(e);
        }
    }

    public static <T> List<T> getEntities(final Class<T> T, final int nbRecord, final int pageNumber) {
        final WebTarget webTarget = RestClient.client.target(RestClient.API_URL)
                .path(T.getSimpleName().toLowerCase()).queryParam("recordsByPage", nbRecord)
                .queryParam("pageNumber", pageNumber);
        return webTarget.request(MediaType.APPLICATION_JSON).get(new GenericType<List<T>>() {
        });
    }

    public static <T> Optional<T> getEntity(final Class<T> T, final long entityId) {
        final WebTarget webTarget = RestClient.client.target(RestClient.API_URL)
                .path(T.getSimpleName().toLowerCase() + "/" + entityId);
        try {
            return Optional.ofNullable(webTarget.request(MediaType.APPLICATION_JSON_TYPE).get(T));
        } catch (final Exception e) {
            throw new RestClientException(e);
        }
    }

    public static <T> void removeEntity(final Class<T> T, final long entityId) {
        final WebTarget webTarget = RestClient.client.target(RestClient.API_URL)
                .path(T.getSimpleName().toLowerCase() + "/" + entityId);
        try {
            webTarget.request(MediaType.APPLICATION_JSON_TYPE).delete();
        } catch (final Exception e) {
            throw new RestClientException(e);
        }
    }

    public static Optional<Computer> updateComputer(final Computer computer) {
        final WebTarget webTarget = RestClient.client.target(RestClient.API_URL).path("computer");
        final Response response = webTarget.request(MediaType.APPLICATION_JSON_TYPE)
                .put(Entity.entity(computer, MediaType.APPLICATION_JSON_TYPE));

        switch (response.getStatus()) {
            case 400:
                throw new RestClientException(response.readEntity(String.class));
            default:
                return Optional.ofNullable(response.readEntity(Computer.class));
        }
    }

}
