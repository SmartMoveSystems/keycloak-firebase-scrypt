package com.smartmovesystems.keycloak.firebasescrypt;

import jakarta.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ScryptParametersMockProvider implements ScryptParametersProvider {

    private final List<ScryptHashParametersRepresentation> parametersEntityList = new ArrayList<>();

    public void add(ScryptHashParametersRepresentation scryptHashParameters) {
        parametersEntityList.add(scryptHashParameters);
    }

    public void clear() {
        parametersEntityList.clear();
    }

    @Override
    public ScryptHashParametersRepresentation addParameters(ScryptHashParametersRepresentation rep) {
        rep.setId(UUID.randomUUID().toString());
        parametersEntityList.add(rep);
        return rep;
    }

    @Override
    public ScryptHashParametersRepresentation getHashParametersById(String parametersId) {
        ScryptHashParametersRepresentation params = parametersEntityList
                .stream()
                .filter(p -> parametersId.equals(p.getId()))
                .findFirst()
                .orElse(null);
        if (params == null) {
            throw new NoResultException();
        }
        return params;
    }

    @Override
    public ScryptHashParametersRepresentation getDefaultParameters() {
        ScryptHashParametersRepresentation def = parametersEntityList
                .stream()
                .filter(ScryptHashParametersRepresentation::isDefault)
                .findFirst()
                .orElse(null);
        if (def == null) {
            throw new NoResultException();
        }
        return def;
    }

    @Override
    public List<ScryptHashParametersRepresentation> getAllParameters() {
        return parametersEntityList;
    }

    @Override
    public void close() {

    }
}
