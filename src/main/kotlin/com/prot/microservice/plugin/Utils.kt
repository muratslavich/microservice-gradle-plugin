package com.prot.microservice.plugin

import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler

class Utils {
}

internal val Project.microserviceExtension: MicroservicePluginExtension
    get() = extensions.getByType(MicroservicePluginExtension::class.java)

internal val Project.detektExtension: DetektExtension
    get() = extensions.getByType(DetektExtension::class.java)

fun DependencyHandler.`implementation`(dependencyNotation: Any): Dependency =
    add("implementation", dependencyNotation)!!

fun DependencyHandler.`testImplementation`(dependencyNotation: Any): Dependency =
    add("testImplementation", dependencyNotation)!!

fun DependencyHandler.`testRuntimeOnly`(dependencyNotation: Any): Dependency =
    add("testRuntimeOnly", dependencyNotation)!!

fun DependencyHandler.`compileOnly`(dependencyNotation: Any): Dependency =
    add("compileOnly", dependencyNotation)!!

fun DependencyHandler.`annotationProcessor`(dependencyNotation: Any): Dependency =
    add("annotationProcessor", dependencyNotation)!!

fun DependencyHandler.`testCompileOnly`(dependencyNotation: Any): Dependency =
    add("testCompileOnly", dependencyNotation)!!

fun DependencyHandler.`testAnnotationProcessor`(dependencyNotation: Any): Dependency =
    add("testAnnotationProcessor", dependencyNotation)!!
