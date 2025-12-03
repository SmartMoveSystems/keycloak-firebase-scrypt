package com.smartmovesystems.keycloak.firebasescrypt;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.*;
import org.keycloak.policy.PasswordPolicyProvider;
import org.keycloak.provider.InvalidationHandler;
import org.keycloak.provider.Provider;
import org.keycloak.services.clientpolicy.ClientPolicyManager;
import org.keycloak.sessions.AuthenticationSessionProvider;
import org.keycloak.vault.VaultTranscriber;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class KeycloakSessionMock implements KeycloakSession {
    @Override
    public KeycloakContext getContext() {
        return null;
    }

    @Override
    public KeycloakTransactionManager getTransactionManager() {
        return null;
    }

    @Override
    public <T extends Provider> T getProvider(Class<T> clazz) {
        return null;
    }

    @Override
    public <T extends Provider> T getProvider(Class<T> clazz, String id) {
        if (clazz == PasswordPolicyProvider.class) {
            return (T) new PasswordPolicyMockProvider();
        }
        return null;
    }

    /**
     * Returns a component provider for a component from the realm that is relevant to this session.
     * The relevant realm must be set prior to calling this method in the context, see {@link KeycloakContext#getRealm()}.
     *
     * @param clazz
     * @param componentId Component configuration
     * @return Provider configured according to the {@param componentId}, {@code null} if it cannot be instantiated.
     * @throws IllegalArgumentException If the realm is not set in the context.
     */
    @Override
    public <T extends Provider> T getComponentProvider(final Class<T> clazz, final String componentId) {
        return null;
    }

    /**
     * Returns a component provider for a component from the realm that is relevant to this session.
     * The relevant realm must be set prior to calling this method in the context, see {@link KeycloakContext#getRealm()}.
     *
     * @param clazz
     * @param componentId Component configuration
     * @param modelGetter Getter to retrieve componentModel
     * @return Provider configured according to the {@param componentId}, {@code null} if it cannot be instantiated.
     * @throws IllegalArgumentException If the realm is not set in the context.
     */
    @Override
    public <T extends Provider> T getComponentProvider(final Class<T> clazz, final String componentId, final Function<KeycloakSessionFactory, ComponentModel> modelGetter) {
        return null;
    }

    @Override
    public <T extends Provider> T getProvider(Class<T> clazz, ComponentModel componentModel) {
        return null;
    }

    @Override
    public <T extends Provider> Set<String> listProviderIds(Class<T> clazz) {
        return null;
    }

    @Override
    public <T extends Provider> Set<T> getAllProviders(Class<T> clazz) {
        return null;
    }

    @Override
    public Class<? extends Provider> getProviderClass(String providerClassName) {
        return null;
    }

    @Override
    public Object getAttribute(String attribute) {
        return null;
    }

    @Override
    public <T> T getAttribute(String attribute, Class<T> clazz) {
        return null;
    }

    @Override
    public <T> T getAttributeOrDefault(String attribute, T defaultValue) {
        return null;
    }

    @Override
    public Object removeAttribute(String attribute) {
        return null;
    }

    @Override
    public void setAttribute(String name, Object value) {

    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }

    /**
     * Invalidates intermediate states of the given objects, both immediately and at the end of this session.
     *
     * @param type   Type of the objects to invalidate
     * @param params Parameters used for the invalidation
     */
    @Override
    public void invalidate(final InvalidationHandler.InvalidableObjectType type, final Object... params) {

    }

    @Override
    public void enlistForClose(Provider provider) {

    }

    @Override
    public KeycloakSessionFactory getKeycloakSessionFactory() {
        return null;
    }

    @Override
    public RealmProvider realms() {
        return null;
    }

    @Override
    public ClientProvider clients() {
        return null;
    }

    /**
     * Returns a managed provider instance.  Will start a provider transaction.  This transaction is managed by the KeycloakSession
     * transaction.
     *
     * @return Currently used ClientScopeProvider instance.
     * @throws IllegalStateException if transaction is not active
     */
    @Override
    public ClientScopeProvider clientScopes() {
        return null;
    }

    /**
     * Returns a managed group provider instance.
     *
     * @return Currently used GroupProvider instance.
     * @throws IllegalStateException if transaction is not active
     */
    @Override
    public GroupProvider groups() {
        return null;
    }

    /**
     * Returns a managed provider instance.  Will start a provider transaction.  This transaction is managed by the KeycloakSession
     * transaction.
     *
     * @return
     * @throws IllegalStateException if transaction is not active
     */
    @Override
    public RoleProvider roles() {
        return null;
    }

    @Override
    public UserSessionProvider sessions() {
        return null;
    }

    /**
     * Returns a managed provider instance.  Will start a provider transaction.  This transaction is managed by the KeycloakSession
     * transaction.
     *
     * @return {@link UserLoginFailureProvider}
     * @throws IllegalStateException if transaction is not active
     */
    @Override
    public UserLoginFailureProvider loginFailures() {
        return null;
    }

    @Override
    public AuthenticationSessionProvider authenticationSessions() {
        return null;
    }

    @Override
    public SingleUseObjectProvider singleUseObjects() {
        return null;
    }

    /**
     * Returns the default IDP provider .
     *
     * @return the default IDP provider.
     */
    @Override
    public IdentityProviderStorageProvider identityProviders() {
        return null;
    }

    @Override
    public void close() {

    }

    @Override
    public UserProvider users() {
        return null;
    }

    @Override
    public KeyManager keys() {
        return null;
    }

    @Override
    public ThemeManager theme() {
        return null;
    }

    @Override
    public TokenManager tokens() {
        return null;
    }

    @Override
    public VaultTranscriber vault() {
        return null;
    }

    @Override
    public ClientPolicyManager clientPolicy() {
        return null;
    }

    @Override
    public boolean isClosed() {
        return false;
    }
}
