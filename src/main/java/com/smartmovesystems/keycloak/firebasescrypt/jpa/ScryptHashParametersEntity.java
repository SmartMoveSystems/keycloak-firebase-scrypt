package com.smartmovesystems.keycloak.firebasescrypt.jpa;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "SCRYPT_PARAMS")
@NamedQueries({
    @NamedQuery(name = "findDefault", query = "SELECT s FROM ScryptHashParametersEntity s WHERE s.isDefault = TRUE"),
    @NamedQuery(name = "findById", query = "SELECT s FROM ScryptHashParametersEntity s WHERE s.id = :id"),
    @NamedQuery(name = "findAll", query = "SELECT s FROM ScryptHashParametersEntity s")
})
public class ScryptHashParametersEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", length = 36)
    @Access(AccessType.PROPERTY)
    protected String id;

    @Column(name = "ROUNDS")
    private int rounds;

    @Column(name = "MEM_COST")
    private int memCost;

    @Column(name = "SIGNER", length = 128)
    private String baser64Signer;

    @Column(name = "SALT_SEP", length = 64)
    private String saltSeparator;

    @Column(name = "IS_DEFAULT")
    private boolean isDefault;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public int getMemCost() {
        return memCost;
    }

    public void setMemCost(int memCost) {
        this.memCost = memCost;
    }

    public String getBase64Signer() {
        return baser64Signer;
    }

    public void setBaser64Signer(String baser64Signer) {
        this.baser64Signer = baser64Signer;
    }

    public String getSaltSeparator() {
        return saltSeparator;
    }

    public void setSaltSeparator(String saltSeparator) {
        this.saltSeparator = saltSeparator;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScryptHashParametersEntity that = (ScryptHashParametersEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
