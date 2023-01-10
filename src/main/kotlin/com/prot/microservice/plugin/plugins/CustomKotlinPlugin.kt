package com.prot.microservice.plugin.plugins

import com.prot.microservice.plugin.detektExtension
import com.prot.microservice.plugin.implementation
import com.prot.microservice.plugin.microserviceExtension
import com.prot.microservice.plugin.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinBasePlugin

class CustomKotlinPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = target.run {
        val kotlin = microserviceExtension.getKotlin()

        kotlinExtension.jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of(kotlin.jvmTargetVersion.get()))
        }

        detektExtension.apply {
            toolVersion = kotlin.detektVersion.get()
            ignoreFailures = true
            autoCorrect = false
        }

        plugins.withType<KotlinBasePlugin> {
            mkdir( "${projectDir}/src/main/kotlin/${project.group.toString().replace('.', '/')}" )
            mkdir( "${projectDir}/src/test/kotlin/${project.group.toString().replace('.', '/')}" )
        }

        afterEvaluate {
            plugins.withId("org.springframework.boot") {
                dependencies.apply {
                    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
                    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.2")
                    testImplementation("io.mockk:mockk:1.13.3")
                }

                pluginManager.run {
                    apply("org.jetbrains.kotlin.plugin.spring")
                    apply("org.jetbrains.kotlin.plugin.jpa")
                }
            }
        }
    }
}