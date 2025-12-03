package com.smartmovesystems.keycloak.firebasescrypt.impl;

import com.smartmovesystems.keycloak.firebasescrypt.ScryptHashParametersRepresentation;
import com.smartmovesystems.keycloak.firebasescrypt.ScryptParametersProvider;
import com.smartmovesystems.keycloak.firebasescrypt.jpa.ScryptHashParametersEntity;
import com.smartmovesystems.keycloak.firebasescrypt.jpa.ScryptParametersEntityProvider;
import org.keycloak.connections.jpa.JpaConnectionProvider;
import org.keycloak.models.KeycloakSession;

import jakarta.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

public class ScryptParametersProviderImpl implements ScryptParametersProvider {

    private final KeycloakSession session;

    public ScryptParametersProviderImpl(KeycloakSession session) {
        this.session = session;
    }

    private ScryptParametersEntityProvider getEntityProvider() {
        return new ScryptParametersEntityProvider(session.getProvider(JpaConnectionProvider.class).getEntityManager());
    }

    @Override
    public ScryptHashParametersRepresentation addParameters(ScryptHashParametersRepresentation rep) {
        return new ScryptHashParametersRepresentation(getEntityProvider().addHashParameters(rep));
    }

    @Override
    public ScryptHashParametersRepresentation getHashParametersById(String parametersId) {
        return new ScryptHashParametersRepresentation(getEntityProvider().getHashParametersById(parametersId));
    }

    @Override
    public ScryptHashParametersRepresentation getDefaultParameters() throws NoResultException {
        return new ScryptHashParametersRepresentation(getEntityProvider().getDefaultParameters());
    }

    @Override
    public List<ScryptHashParametersRepresentation> getAllParameters() {
        List<ScryptHashParametersEntity> entities = getEntityProvider().getAllParameters();
        List<ScryptHashParametersRepresentation> result = new ArrayList<>();
        for (ScryptHashParametersEntity entity : entities) {
            result.add(new ScryptHashParametersRepresentation(entity));
        }
        return result;
    }

    @Override
    public void close() {

    }
}
