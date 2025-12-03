package com.smartmovesystems.keycloak.firebasescrypt.rest;

import org.keycloak.models.KeycloakSession;
import org.keycloak.services.managers.AppAuthManager;
import org.keycloak.services.managers.AuthenticationManager;

import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.Path;

public class ScryptHashParametersRestResource {
    private final KeycloakSession session;
    private final AuthenticationManager.AuthResult auth;

    public ScryptHashParametersRestResource(KeycloakSession session) {
        this.session = session;
        this.auth = new AppAuthManager.BearerTokenAuthenticator(session).authenticate();
    }

    /**
     * Checks that requesting user has admin access
     * @return
     */
    @Path("")
    public ScryptHashParametersResource getHashParametersResourceAuthenticated() {
        checkRealmAdmin();
        return new ScryptHashParametersResource(session);
    }

    private void checkRealmAdmin() {
        if (auth == null) {
            throw new NotAuthorizedException("Bearer");
        } else if (auth.getToken().getRealmAccess() == null || !auth.getToken().getRealmAccess().isUserInRole("admin")) {
            throw new ForbiddenException("Does not have realm admin role");
        }
    }
}
