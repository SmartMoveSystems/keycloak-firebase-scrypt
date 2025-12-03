ARG keycloak_version=26.4.6

FROM quay.io/keycloak/keycloak:${keycloak_version}

ARG keycloak_firebase_scrypt_version=3.0.4

COPY ./keycloak-firebase-scrypt-${keycloak_firebase_scrypt_version}.jar /opt/keycloak/providers/
#COPY ./themes/ /opt/jboss/keycloak/standalone/deployments/
#COPY ./startup-scripts/ /opt/jboss/startup-scripts/
