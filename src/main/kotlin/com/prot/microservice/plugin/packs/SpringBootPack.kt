package com.prot.microservice.plugin.packs

import com.prot.microservice.plugin.implementation
import com.prot.microservice.plugin.testImplementation
import io.spring.gradle.dependencymanagement.DependencyManagementPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.provider.Property
import org.springframework.boot.gradle.plugin.SpringBootPlugin

abstract class SpringBootPack : GenericPack {

    abstract val springBootVersion: Property<String>
    abstract val bomVersion: Property<String>

    init {
        this.springBootVersion.convention("3.0.1")
        this.bomVersion.convention("1.1.0")
    }

    override fun getAllPlugins(): List<Class<out Plugin<Project>>> {
        return listOf(
            SpringBootPlugin::class.java,
            DependencyManagementPlugin::class.java,
        )
    }

    override fun addDeclaredDependencies(dh: DependencyHandler): Unit = with(dh) {
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.boot:spring-boot-starter-web")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }

}