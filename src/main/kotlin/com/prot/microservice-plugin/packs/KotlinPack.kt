package com.prot.`microservice-plugin`.packs

import com.prot.`microservice-plugin`.implementation
import com.prot.`microservice-plugin`.plugins.CustomKotlinPlugin
import com.prot.`microservice-plugin`.testImplementation
import io.gitlab.arturbosch.detekt.DetektPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.kotlin
import org.jetbrains.kotlin.gradle.internal.Kapt3GradleSubplugin
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper

// https://docs.spring.io/spring-boot/docs/current/reference/html/dependency-versions.html#appendix.dependency-versions.coordinates
abstract class KotlinPack : GenericPack {
    abstract val kotlinVersion: Property<String>
    abstract val jvmTargetVersion: Property<String>
    abstract val coroutinesVersion: Property<String>
    abstract val kotestVersion: Property<String>
    abstract val detektVersion: Property<String>

    init {
        kotlinVersion.convention("1.7.22")
        jvmTargetVersion.convention("17")
        coroutinesVersion.convention("1.6.4")
        kotestVersion.convention("5.5.4")
        detektVersion.convention("1.22.0")
    }

    override fun addDeclaredDependencies(dh: DependencyHandler) {
        with(dh) {
            implementation(enforcedPlatform("org.jetbrains.kotlin:kotlin-bom:${kotlinVersion.get()}"))
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${coroutinesVersion.get()}")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:${coroutinesVersion.get()}")
            testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${coroutinesVersion.get()}")

            implementation("org.jetbrains.kotlin:kotlin-reflect")

            testImplementation("io.kotest:kotest-assertions-core:${kotestVersion.get()}")
            testImplementation("io.kotest:kotest-assertions-core-jvm:${kotestVersion.get()}")
            testImplementation("io.kotest:kotest-runner-junit5-jvm:${kotestVersion.get()}")
            testImplementation("io.kotest:kotest-property:${kotestVersion.get()}")
            testImplementation(kotlin("test", version = kotlinVersion.get()))
        }
    }

    override fun getAllPlugins(): List<Class<out Plugin<Project>>> {
        return listOf(
            KotlinPluginWrapper::class.java,
            Kapt3GradleSubplugin::class.java,
            DetektPlugin::class.java,
            CustomKotlinPlugin::class.java
        )
    }
}