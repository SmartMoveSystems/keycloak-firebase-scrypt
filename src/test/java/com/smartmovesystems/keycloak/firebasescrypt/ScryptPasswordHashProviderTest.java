package com.smartmovesystems.keycloak.firebasescrypt;


import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.keycloak.credential.hash.Pbkdf2Sha512PasswordHashProviderFactory; // using Pbkdf2Sha512PasswordHashProviderFactory.ID that was org.keycloak.models.PasswordPolicy.HASH_ALGORITHM_DEFAULT
import org.keycloak.models.PasswordPolicy;
import org.keycloak.models.credential.PasswordCredentialModel;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ScryptPasswordHashProviderTest {

    private ScryptPasswordHashProvider hashProvider;
    private ScryptHashParametersRepresentation parametersEntityOne;
    private ScryptHashParametersRepresentation parametersEntityTwo;
    private ScryptParametersMockProvider mockProvider;

    @BeforeEach
    public void setUp() {
        mockProvider = new ScryptParametersMockProvider();

        parametersEntityOne = new ScryptHashParametersRepresentation();
        parametersEntityOne.id = UUID.randomUUID().toString();
        parametersEntityOne.setBase64Signer("jxspr8Ki0RYycVU8zykbdLGjFQ3McFUH0uiiTvC8pVMXAn210wjLNmdZJzxUECKbm0QsEmYUSDzZvpjeJ9WmXA==");
        parametersEntityOne.setMemCost(14);
        parametersEntityOne.setRounds(8);
        parametersEntityOne.setSaltSeparator("Bw==");
        parametersEntityOne.setDefault(true);

        parametersEntityTwo = new ScryptHashParametersRepresentation();
        parametersEntityTwo.id = UUID.randomUUID().toString();
        parametersEntityTwo.setBase64Signer("jxspr8Ki0ABCDVU8zykbdLGjFQ3McFUH0uiiTvC8pVMX1234567LNmdZJzxUECKbm0QsEmYUSDzZvpjeJ9WmXA==");
        parametersEntityTwo.setMemCost(14);
        parametersEntityTwo.setRounds(8);
        parametersEntityTwo.setSaltSeparator("Bw==");
        parametersEntityTwo.setDefault(false);

        mockProvider.add(parametersEntityOne);
        mockProvider.add(parametersEntityTwo);

        hashProvider = new ScryptPasswordHashProvider(ScryptPasswordHashProviderFactory.ID, mockProvider, new SaltMockProvider());
    }

    @Test
    @DisplayName("policyCheck")
    public void policyCheck() {
        PasswordCredentialModel model = PasswordCredentialModel.createFromValues(
                ScryptPasswordHashProviderFactory.ID,
                Base64.decodeBase64("42xEC+ixf3L2lw=="),
                0,
                "lSrfV15cpx95/sZS2W9c9Kp6i/LVgQNDNC/qzrCnh1SAyZvqmZqAjTdn3aoItz+VHjoZilo78198JAdRuid5lQ=="
        );
        PasswordPolicy policy = PasswordPolicy.build()
                .put(PasswordPolicy.HASH_ALGORITHM_ID, ScryptPasswordHashProviderFactory.ID)
                .build(new KeycloakSessionMock());
        boolean valid = hashProvider.policyCheck(policy, model);
        assertTrue(valid);
    }

    @Test
    @DisplayName("policyCheckWrongPolicyAlgorithm")
    public void policyCheckWrongPolicyAlgorithm() {
        PasswordCredentialModel model = PasswordCredentialModel.createFromValues(
                ScryptPasswordHashProviderFactory.ID,
                Base64.decodeBase64("42xEC+ixf3L2lw=="),
                0,
                "lSrfV15cpx95/sZS2W9c9Kp6i/LVgQNDNC/qzrCnh1SAyZvqmZqAjTdn3aoItz+VHjoZilo78198JAdRuid5lQ=="
        );
        PasswordPolicy policy = PasswordPolicy.build()
                .put(PasswordPolicy.HASH_ALGORITHM_ID, Pbkdf2Sha512PasswordHashProviderFactory.ID)
                .build(new KeycloakSessionMock());
        boolean valid = hashProvider.policyCheck(policy, model);
        assertFalse(valid);
    }

    @Test
    @DisplayName("policyCheckWrongCredentialAlgorithm")
    public void policyCheckWrongCredentialAlgorithm() {
        PasswordCredentialModel model = PasswordCredentialModel.createFromValues(
                Pbkdf2Sha512PasswordHashProviderFactory.ID,
                Base64.decodeBase64("42xEC+ixf3L2lw=="),
                0,
                "lSrfV15cpx95/sZS2W9c9Kp6i/LVgQNDNC/qzrCnh1SAyZvqmZqAjTdn3aoItz+VHjoZilo78198JAdRuid5lQ=="
        );
        PasswordPolicy policy = PasswordPolicy.build()
                .put(PasswordPolicy.HASH_ALGORITHM_ID, ScryptPasswordHashProviderFactory.ID)
                .build(new KeycloakSessionMock());
        boolean valid = hashProvider.policyCheck(policy, model);
        assertFalse(valid);
    }

    @Test
    @DisplayName("encodedCredential")
    public void encodedCredential() {
        // Expect to use default hash parameters
        String expected = "{\"value\":\"lSrfV15cpx95/sZS2W9c9Kp6i/LVgQNDNC/qzrCnh1SAyZvqmZqAjTdn3aoItz+VHjoZilo78198JAdRuid5lQ==$" + parametersEntityOne.id + "\",\"salt\":\"42xEC+ixf3L2lw==\",\"additionalParameters\":{}}";
        PasswordCredentialModel encoded = hashProvider.encodedCredential("user1password", 0);
        assertEquals(expected, encoded.getSecretData());
    }

    @Test
    @DisplayName("encodedCredentialWithoutDefault")
    public void encodedCredentialWithoutDefault() {
        mockProvider.clear();
        PasswordCredentialModel model = hashProvider.encodedCredential("test", 0);
        assertEquals("HUoznV1w4qjGMFUe2gKz+fmGai83WSaerGPwGYJvzXGHTMkwsaHqT1gWlFCFZwk7ei2HmD4pp8OXEiaavv+OEA==", model.getPasswordSecretData().getValue());
        assertEquals("42xEC+ixf3L2lw==", new String(Base64.encodeBase64(model.getPasswordSecretData().getSalt())));
    }

    @Test
    @DisplayName("verifyWithoutDefault")
    public void verifyWithoutDefault() {
        mockProvider.clear();
        boolean verified = hashProvider.verify("test", hashProvider.encodedCredential("test", 0));
        assertTrue(verified);
    }

    @Test
    @DisplayName("verifyUserOne")
    public void verifyUserOne() {
        PasswordCredentialModel model = PasswordCredentialModel.createFromValues(
                ScryptPasswordHashProviderFactory.ID,
                Base64.decodeBase64("42xEC+ixf3L2lw=="),
                0,
                "lSrfV15cpx95/sZS2W9c9Kp6i/LVgQNDNC/qzrCnh1SAyZvqmZqAjTdn3aoItz+VHjoZilo78198JAdRuid5lQ==$" +  parametersEntityOne.id
        );

        model.setId("1");

        boolean verified = hashProvider.verify("user1password", model);
        assertTrue(verified);
    }

    @Test
    @DisplayName("verifyUserOneBadPassword")
    public void verifyUserOneBadPassword() {
        PasswordCredentialModel model = PasswordCredentialModel.createFromValues(
                ScryptPasswordHashProviderFactory.ID,
                Base64.decodeBase64("42xEC+ixf3L2lw=="),
                0,
                "lSrfV15cpx95/sZS2W9c9Kp6i/LVgQNDNC/qzrCnh1SAyZvqmZqAjTdn3aoItz+VHjoZilo78198JAdRuid5lQ==$" +  parametersEntityOne.id
        );

        model.setId("1");

        boolean verified = hashProvider.verify("user1password_", model);
        assertFalse(verified);
    }

    @Test
    @DisplayName("verifyUserTwo")
    public void verifyUserTwo() {
        PasswordCredentialModel model = PasswordCredentialModel.createFromValues(
                ScryptPasswordHashProviderFactory.ID,
                Base64.decodeBase64("42xEC+ixf3L2lw=="),
                0,
                "lSrfV15cpx95/sZS2W9c9Kp6i/LVgQNDNC/qzrCnh1SAyZvqmZqAjTdn3aoItz+VHjoZilo78198JAdRuid5lQ=="
        );

        model.setId("2");

        boolean verified = hashProvider.verify("user1password", model);
        assertTrue(verified);
    }

    @Test
    @DisplayName("verifyUserTwoBadPassword")
    public void verifyUserTwoBadPassword() {
        PasswordCredentialModel model = PasswordCredentialModel.createFromValues(
                ScryptPasswordHashProviderFactory.ID,
                Base64.decodeBase64("42xEC+ixf3L2lw=="),
                0,
                "lSrfV15cpx95/sZS2W9c9Kp6i/LVgQNDNC/qzrCnh1SAyZvqmZqAjTdn3aoItz+VHjoZilo78198JAdRuid5lQ=="
        );

        model.setId("2");

        boolean verified = hashProvider.verify("_user1password", model);
        assertFalse(verified);
    }

    @Test
    @DisplayName("verifyUserThree")
    public void verifyUserThree() {
        // Use non-default parameters
        PasswordCredentialModel model = PasswordCredentialModel.createFromValues(
                ScryptPasswordHashProviderFactory.ID,
                Base64.decodeBase64("42xEC+ixf3L2lw=="),
                0,
                "lSrfV15cpgkJgsZS2W9c9Kp6i/LVgQNDNC/qzrCnh1SAHIunrTyAjTdn3aoItz+VHjoZilo78198JAdRuid5lQ==$" +  parametersEntityTwo.id
        );

        model.setId("3");

        boolean verified = hashProvider.verify("user1password", model);
        assertTrue(verified);
    }

    @Test
    @DisplayName("verifyUserThreeBadPassword")
    public void verifyUserThreeBadPassword() {
        // Use non-default parameters
        PasswordCredentialModel model = PasswordCredentialModel.createFromValues(
                ScryptPasswordHashProviderFactory.ID,
                Base64.decodeBase64("42xEC+ixf3L2lw=="),
                0,
                "lSrfV15cpgkJgsZS2W9c9Kp6i/LVgQNDNC/qzrCnh1SAHIunrTyAjTdn3aoItz+VHjoZilo78198JAdRuid5lQ==$" +  parametersEntityTwo.id
        );

        model.setId("3");

        boolean verified = hashProvider.verify("_user1password", model);
        assertFalse(verified);
    }
}