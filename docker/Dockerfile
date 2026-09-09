ARG keycloak_version=26.7.3

FROM quay.io/keycloak/keycloak:${keycloak_version}

ARG keycloak_firebase_scrypt_version=4.0.0

COPY ./keycloak-firebase-scrypt-${keycloak_firebase_scrypt_version}.jar /opt/keycloak/providers/
#COPY ./themes/ /opt/keycloak/providers/
#COPY ./startup-scripts/ /opt/jboss/startup-scripts/
