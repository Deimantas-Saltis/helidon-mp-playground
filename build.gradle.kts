import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
    application
    id("org.kordamp.gradle.jandex") version "0.6.0"
}

group = "com.helidon"
version = "1.0-SNAPSHOT"

java {
    targetCompatibility = JavaVersion.VERSION_17
    sourceCompatibility = JavaVersion.VERSION_17
}


repositories {
    mavenCentral()
}

dependencies {
    implementation(enforcedPlatform("io.helidon:helidon-dependencies:3.1.2"))

    implementation("io.helidon.microprofile.metrics:helidon-microprofile-metrics")
    implementation("io.helidon.microprofile.health:helidon-microprofile-health")
    implementation("io.helidon.microprofile.messaging:helidon-microprofile-messaging")
    implementation("io.helidon.microprofile.messaging:helidon-microprofile-messaging-health")
    implementation("io.helidon.microprofile.tracing:helidon-microprofile-tracing")

    implementation("io.helidon.tracing:helidon-tracing-jaeger")
    implementation("io.helidon.messaging.kafka:helidon-messaging-kafka")
    implementation("io.helidon.config:helidon-config-yaml")
    implementation("io.helidon.media:helidon-media-jackson")

    implementation("jakarta.persistence:jakarta.persistence-api")
    implementation("jakarta.transaction:jakarta.transaction-api")

    implementation("org.glassfish.jersey.media:jersey-media-json-jackson")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")


    implementation("org.flywaydb:flyway-core:9.14.1")
    implementation("org.eclipse.persistence:org.eclipse.persistence.jpa:2.7.10")
    implementation("org.postgresql:postgresql:42.2.27")

    runtimeOnly("io.helidon.integrations.cdi:helidon-integrations-cdi-datasource-hikaricp")
    runtimeOnly("io.helidon.integrations.cdi:helidon-integrations-cdi-jta-weld")
    runtimeOnly("io.helidon.integrations.cdi:helidon-integrations-cdi-jpa")
    runtimeOnly("io.helidon.integrations.cdi:helidon-integrations-cdi-eclipselink")
    runtimeOnly("org.jboss:jandex")
    runtimeOnly("jakarta.activation:jakarta.activation-api")
}

application {
    mainClass.set("io.helidon.microprofile.cdi.Main")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

tasks.register("moveBeansXML") {
    doLast {
        ant.withGroovyBuilder {
            "move"(
                "file" to "${buildDir}/resources/main/META-INF/beans.xml",
                "todir" to "${buildDir}/classes/kotlin/main/META-INF",
            )
        }
    }
}

tasks.withType<JavaExec> {
    dependsOn("moveBeansXML")
}