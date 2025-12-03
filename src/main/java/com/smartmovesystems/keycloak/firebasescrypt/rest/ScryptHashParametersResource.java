package com.smartmovesystems.keycloak.firebasescrypt.rest;

import com.smartmovesystems.keycloak.firebasescrypt.ScryptHashParametersRepresentation;
import com.smartmovesystems.keycloak.firebasescrypt.ScryptParametersProvider;
import org.jboss.resteasy.reactive.NoCache;
import org.keycloak.models.KeycloakSession;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

public class ScryptHashParametersResource {

    private final KeycloakSession session;

    public ScryptHashParametersResource(KeycloakSession session) {
        this.session = session;
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createHashParameters(ScryptHashParametersRepresentation rep) {
        ScryptHashParametersRepresentation created = session.getProvider(ScryptParametersProvider.class).addParameters(rep);
        return Response.created(session.getContext().getUri().getAbsolutePathBuilder().path(created.getId()).build()).build();
    }

    @GET
    @NoCache
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ScryptHashParametersRepresentation> getAllParameters() {
        return session.getProvider(ScryptParametersProvider.class).getAllParameters();
    }
}
