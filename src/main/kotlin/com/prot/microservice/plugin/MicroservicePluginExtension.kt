package com.prot.microservice.plugin

import com.prot.microservice.plugin.packs.KotlinPack
import com.prot.microservice.plugin.packs.SpringBootPack
import com.prot.microservice.plugin.packs.SpringPack
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.PluginManager

open class MicroservicePluginExtension(private val project: Project) {

    private val objects by lazy { project.objects }
    private val kotlin: Lazy<KotlinPack> = lazyInstance()
    private val spring: Lazy<SpringPack> = lazyInstance()
    private val springBoot: Lazy<SpringBootPack> = lazyInstance()
    private val container = listOf(kotlin, spring, springBoot)

    fun kotlin(action: Action<KotlinPack>) = action.execute(kotlin.value)
    fun spring(action: Action<SpringPack>) = action.execute(spring.value)
    fun springBoot(action: Action<SpringBootPack>) = action.execute(springBoot.value)

    fun deps(dependencies: DependencyHandler) {
        container.forEach {
            if (it.isInitialized()) it.value.addDeclaredDependencies(dependencies)
        }
    }

    fun plugins(pluginManager: PluginManager) {
        container.forEach {
            if (it.isInitialized()) {
                it.value.getAllPlugins().forEach { plugin -> pluginManager.apply(plugin) }
            }
        }
    }

    fun getKotlin() = kotlin.also {
        assert( kotlin.isInitialized() ) { "Kotlin doesn't use" }
    }.value

    private inline fun <reified T> lazyInstance() = lazy { objects.newInstance(T::class.java) }
}
