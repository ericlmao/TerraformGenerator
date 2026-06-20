import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.jvm.toolchain.JavaToolchainService

plugins {
    java;
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.21" apply false
}

subprojects {
    apply<JavaPlugin>()

    group = "org.terraform"
    repositories {
        mavenCentral()
        mavenLocal()
        maven("https://hub.spigotmc.org/nexus/content/repositories/public/")
        maven("https://repo.codemc.io/repository/nms/")
		maven("https://repo.papermc.io/repository/maven-public/")
		maven("https://jitpack.io")
    }

    val javaToolchains = extensions.getByType<JavaToolchainService>()
    tasks.withType<JavaCompile>().configureEach {
        if (project.name.contains("v26")) {
            javaCompiler.set(javaToolchains.compilerFor {
                languageVersion.set(JavaLanguageVersion.of(26))
            })
            options.release.set(25)
        }
    }

    tasks.matching { it.name == "reobfJar" }.configureEach {
        if (project.name.contains("v26")) {
            enabled = false
        }
    }

}
