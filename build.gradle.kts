plugins {
    java
}

group = "com.smartmovesystems.keycloak.firebasescrypt"
version = "3.0.4"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    val scryptVersion = "1.4.0"
    val commonsCodecVersion = "1.4"
    val jbossLoggingVersion = "3.4.1.Final"
    val keycloakVersion = "26.4.6" //"12.0.4"
    val jUnitVersion = "5.10.0"
    val quarkusVersion = "3.27.0"

    // Scrypt
    implementation("com.lambdaworks:scrypt:$scryptVersion")

    // Encoding
    implementation("commons-codec:commons-codec:$commonsCodecVersion")

    // JBoss
    compileOnly("org.jboss.logging:jboss-logging:$jbossLoggingVersion")

    // Keycloak
    compileOnly("org.keycloak:keycloak-common:$keycloakVersion")
    compileOnly("org.keycloak:keycloak-core:$keycloakVersion")
    compileOnly("org.keycloak:keycloak-server-spi:$keycloakVersion")
    compileOnly("org.keycloak:keycloak-model-jpa:$keycloakVersion")
    compileOnly("org.keycloak:keycloak-server-spi-private:$keycloakVersion")

    // @NoCache
    compileOnly("io.quarkus.resteasy.reactive:resteasy-reactive-common:$quarkusVersion")

    // JUnit
    testImplementation("org.junit.jupiter:junit-jupiter-api:${jUnitVersion}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${jUnitVersion}")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher") //:1.10.0

    testImplementation("org.keycloak:keycloak-common:$keycloakVersion")
    testImplementation("org.keycloak:keycloak-core:$keycloakVersion")
    testImplementation("org.keycloak:keycloak-server-spi:$keycloakVersion")
    testImplementation("org.keycloak:keycloak-model-jpa:$keycloakVersion")
    testImplementation("org.keycloak:keycloak-server-spi-private:$keycloakVersion")
    testImplementation("org.jboss.logging:jboss-logging:$jbossLoggingVersion")
}


tasks {
    jar {
        from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }) {
            exclude("META-INF/MANIFEST.MF")
            exclude("META-INF/*.SF")
            exclude("META-INF/*.DSA")
            exclude("META-INF/*.RSA")
        }
    }

    wrapper {
        gradleVersion = "9.2"
    }

    test {
        useJUnitPlatform()
    }
}
