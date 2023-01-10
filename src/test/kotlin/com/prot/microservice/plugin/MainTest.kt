package com.prot.microservice.plugin

import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File

class MainTest {

    companion object {
        @TempDir
        lateinit var testProjectDir: File
    }

    private var buildFile = File("src/test/resources/").apply {
        copyRecursively(testProjectDir)
    }
    private var gradleRunner: GradleRunner = GradleRunner.create()
        .withPluginClasspath()
        .withProjectDir(testProjectDir)

    @Test
    fun apply() {

        val result = gradleRunner
            .withArguments("build")
            .build()

        println(result.output)
    }
}