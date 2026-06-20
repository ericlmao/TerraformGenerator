import io.papermc.paperweight.userdev.internal.setup.UserdevSetupTask
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.jvm.toolchain.JavaToolchainService

plugins {
  id("io.papermc.paperweight.userdev") version "2.0.0-beta.21"
}

dependencies {
    compileOnly(project(":common"))
    paperweight.paperDevBundle("26.2.build.+")
	
	compileOnly("org.jetbrains:annotations:20.1.0")
    compileOnly("com.github.AvarionMC:yaml:1.1.7")
}
java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

val javaToolchains = extensions.getByType<JavaToolchainService>()
tasks.withType<UserdevSetupTask>().configureEach {
    launcher.set(javaToolchains.launcherFor {
        languageVersion.set(JavaLanguageVersion.of(26))
    })
}
