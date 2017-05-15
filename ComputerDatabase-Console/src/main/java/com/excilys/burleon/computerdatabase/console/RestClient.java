package com.excilys.burleon.computerdatabase.console;

import java.util.ArrayList;
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
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import com.excilys.burleon.computerdatabase.console.exception.MalformedEntityException;
import com.excilys.burleon.computerdatabase.console.exception.NotFoundEntityException;
import com.excilys.burleon.computerdatabase.console.exception.RestClientException;
import com.excilys.burleon.computerdatabase.console.exception.ServiceUnvailableException;
import com.excilys.burleon.computerdatabase.console.exception.UnauthorizedActionException;
import com.excilys.burleon.computerdatabase.console.exception.UnexpectedResultException;
import com.excilys.burleon.computerdatabase.console.model.Computer;

public class RestClient {

    private final static Client client = ClientBuilder.newClient(new ClientConfig().register(RestClient.class));
    private final static String API_URL = "http://localhost:8080/ComputerDatabase/api/v1";
    private final static HttpAuthenticationFeature httpAuthenticationFeature = HttpAuthenticationFeature
            .basic("admin", "admin");
    static {
        RestClient.client.register(RestClient.httpAuthenticationFeature);
    }

    public static Optional<Computer> createComputer(final Computer computer) throws RestClientException {
        final WebTarget webTarget = RestClient.client.target(RestClient.API_URL).path("computer");
        try {
            final Response response = webTarget.request(MediaType.APPLICATION_JSON_TYPE)
                    .post(Entity.entity(computer, MediaType.APPLICATION_JSON_TYPE));

            if (RestClient.isOkResponse(response)) {
                return Optional.ofNullable(response.readEntity(Computer.class));
            } else {
                return Optional.empty();
            }
        } catch (final Exception e) {
            throw new RestClientException(e);
        }
    }

    public static <T> List<T> getEntities(final Class<T> T, final int nbRecord, final int pageNumber)
            throws RestClientException {
        final WebTarget webTarget = RestClient.client.target(RestClient.API_URL)
                .path(T.getSimpleName().toLowerCase()).queryParam("recordsByPage", nbRecord)
                .queryParam("pageNumber", pageNumber);
        try {
            final Response response = webTarget.request(MediaType.APPLICATION_JSON).get();

            if (RestClient.isOkResponse(response)) {
                return response.readEntity(new GenericType<List<T>>() {
                });
            } else {
                return new ArrayList<>();
            }
        } catch (final Exception e) {
            throw new RestClientException(e);
        }
    }

    public static <T> Optional<T> getEntity(final Class<T> T, final long entityId) throws RestClientException {
        final WebTarget webTarget = RestClient.client.target(RestClient.API_URL)
                .path(T.getSimpleName().toLowerCase() + "/" + entityId);
        try {
            final Response response = webTarget.request(MediaType.APPLICATION_JSON_TYPE).get();

            if (RestClient.isOkResponse(response)) {
                return Optional.ofNullable(response.readEntity(T));
            } else {
                return Optional.empty();
            }
        } catch (final Exception e) {
            throw new RestClientException(e);
        }
    }

    private static boolean isOkResponse(final Response response) {
        switch (response.getStatus()) {
            case 404:
                throw new NotFoundEntityException(response.readEntity(String.class));
            case 400:
                throw new MalformedEntityException(response.readEntity(String.class));
            case 401:
            case 403:
                throw new UnauthorizedActionException(response.readEntity(String.class));
            case 500:
                throw new ServiceUnvailableException(response.readEntity(String.class));
            case 200:
            case 201:
                return true;
            default:
                throw new UnexpectedResultException("Error code that is not treated : " + response.getStatus());
        }
    }

    public static <T> boolean removeEntity(final Class<T> T, final long entityId) throws RestClientException {
        final WebTarget webTarget = RestClient.client.target(RestClient.API_URL)
                .path(T.getSimpleName().toLowerCase() + "/" + entityId);
        try {
            final Response response = webTarget.request(MediaType.APPLICATION_JSON_TYPE).delete();

            if (RestClient.isOkResponse(response)) {
                return true;
            } else {
                return false;
            }
        } catch (final Exception e) {
            throw new RestClientException(e);
        }
    }

    public static Optional<Computer> updateComputer(final Computer computer) throws RestClientException {
        final WebTarget webTarget = RestClient.client.target(RestClient.API_URL).path("computer");
        try {
            final Response response = webTarget.request(MediaType.APPLICATION_JSON_TYPE)
                    .put(Entity.entity(computer, MediaType.APPLICATION_JSON_TYPE));
            if (RestClient.isOkResponse(response)) {
                return Optional.ofNullable(response.readEntity(Computer.class));
            } else {
                return Optional.empty();
            }
        } catch (final Exception e) {
            throw new RestClientException(e);
        }
    }
}
