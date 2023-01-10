package com.prot.`microservice-plugin`.packs

import com.prot.`microservice-plugin`.*
import com.prot.microservice.plugin.*
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.provider.Property

abstract class SpringPack : GenericPack {
    abstract val springVersion: Property<String>
    abstract val lombokVersion: Property<String>

    init {
        this.springVersion.convention("6.0.3")
        this.lombokVersion.convention("1.18.24")
    }

    override fun getAllPlugins(): List<Class<out Plugin<Project>>> {
        return emptyList()
    }

    override fun addDeclaredDependencies(dh: DependencyHandler) {
        dh.apply {
            compileOnly("org.projectlombok:lombok:${lombokVersion.get()}")
            annotationProcessor("org.projectlombok:lombok:${lombokVersion.get()}")
            testCompileOnly("org.projectlombok:lombok:${lombokVersion.get()}")
            testAnnotationProcessor("org.projectlombok:lombok:${lombokVersion.get()}")

            implementation("org.springframework:spring-context:${springVersion.get()}")
            testImplementation("org.springframework:spring-test:${springVersion.get()}")
        }
    }
}