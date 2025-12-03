package com.smartmovesystems.keycloak.firebasescrypt.jpa;

import com.smartmovesystems.keycloak.firebasescrypt.ScryptHashParametersRepresentation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;

public class ScryptParametersEntityProvider {

    private final EntityManager entityManager;

    public ScryptParametersEntityProvider(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    public ScryptHashParametersEntity getHashParametersById(String parametersId) {
        TypedQuery<ScryptHashParametersEntity> query = entityManager.createNamedQuery("findById", ScryptHashParametersEntity.class);
        query.setParameter("id", parametersId);
        return query.getSingleResult();
    }

    public ScryptHashParametersEntity getDefaultParameters() throws NoResultException {
        return entityManager.createNamedQuery("findDefault", ScryptHashParametersEntity.class).getSingleResult();
    }

    public List<ScryptHashParametersEntity> getAllParameters() {
        return entityManager.createNamedQuery("findAll", ScryptHashParametersEntity.class).getResultList();
    }

    public ScryptHashParametersEntity addHashParameters(ScryptHashParametersRepresentation rep) {
        ScryptHashParametersEntity entity = new ScryptHashParametersEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setRounds(rep.getRounds());
        entity.setMemCost(rep.getMemCost());
        entity.setBaser64Signer(rep.getBase64Signer());
        entity.setSaltSeparator(rep.getSaltSeparator());
        entity.setDefault(rep.isDefault());

        entityManager.persist(entity);

        return entity;
    }
}
