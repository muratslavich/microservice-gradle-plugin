package com.prot.microservice.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.getByName

class Main : Plugin<Project> {

    override fun apply(project: Project): Unit = project.run {
        configureCommonProject()
        val pluginExtension = extensions.create<MicroservicePluginExtension>("microservice")

        afterEvaluate {
            pluginExtension.apply {
                plugins(pluginManager)
                deps(dependencies)
            }
        }
    }

    private fun Project.configureCommonProject() {
        // add common plugins for all projects
        pluginManager.apply(JavaPlugin::class.java)
        // add common dependencies for all projects
        dependencies.apply {
            testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
            testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
        }
        // junit5
        tasks.getByName<Test>("test") {
            useJUnitPlatform()
        }
    }
}
