plugins {
    // https://plugins.gradle.org/plugin/org.gradle.kotlin.kotlin-dsl
    `kotlin-dsl`

    // https://plugins.gradle.org/docs/publish-plugin
    // Auto-apply `java-gradle-plugin` plugin
    // Auto-apply `maven-publish` plugin
    id("com.gradle.plugin-publish") version "1.1.0"
}

group = "io.github.muratslavich.microservice-plugin"
version = "0.1.0"

gradlePlugin {
    website.set("https://github.com/muratslavich/microservice-gradle-plugin")
    vcsUrl.set("https://github.com/muratslavich/microservice-gradle-plugin.git")
    plugins {
        create("microservicePlugin") {
            id = "io.github.muratslavich.microservice-plugin"
            displayName = "Plugin for build microservices in prot project"
            description = "Gradle Plugin written in Kotlin for smoothly create and configure Kotlin SpringBoot mvc microservices"
            implementationClass = "com.prot.microservice-plugin.MicroservicePlugin"
            tags.set(listOf("kotlin", "spring", "springboot", "microservices", "mvc"))
        }
    }
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    api(kotlin("stdlib"))
    api("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")
    api("org.jetbrains.kotlin:kotlin-allopen:1.8.0")
    api("org.jetbrains.kotlin:kotlin-noarg:1.8.0")
    api("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.22.0")
    api("org.springframework.boot:spring-boot-gradle-plugin:3.0.1")
    api("io.spring.gradle:dependency-management-plugin:1.1.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
    testImplementation(gradleTestKit())
}

tasks.test {
    useJUnitPlatform()
}
