package io.github.muratslavich.`microservice-plugin`.packs

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler

interface GenericPack {
    fun getAllPlugins(): List<Class<out Plugin<Project>>>
    fun addDeclaredDependencies(dh: DependencyHandler)
}
