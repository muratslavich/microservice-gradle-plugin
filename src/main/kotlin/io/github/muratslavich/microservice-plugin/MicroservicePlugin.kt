package io.github.muratslavich.`microservice-plugin`

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.testing.Test
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.getByName
import java.io.File

class MicroservicePlugin : Plugin<Project> {

    override fun apply(project: Project): Unit = project.run {
        configureCommonProject()
        val pluginExtension = extensions.create<MicroservicePluginExtension>("microservice")

        afterEvaluate {
            pluginExtension.apply {
                deps(dependencies)
                plugins(pluginManager)
            }
            plugins.withId("org.springframework.boot") {
                tasks.getByName<Jar>("bootJar") {
                    destinationDirectory.set(file("out"))
                }
            }
        }
    }

    private fun Project.configureCommonProject() {
        repositories.apply {
            mavenCentral()
        }
        // add common plugins for all projects
        pluginManager.apply(JavaPlugin::class.java)
        // add common dependencies for all projects
        dependencies.apply {
            testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
            testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
        }
        // junit5
        tasks.getByName<Test>("test") {
            useJUnitPlatform()
        }

        tasks.register("createDockerfile") {
            doLast {
                val content = """
                FROM bellsoft/liberica-openjdk-alpine:17
                VOLUME /tmp
                COPY ./artifact/*.jar app.jar
                ENTRYPOINT ["java","-jar","/app.jar"]
                """.trimIndent()
                File("Dockerfile").writeText(content)
            }
        }
    }
}
