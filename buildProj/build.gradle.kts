plugins {
    id("com.gradleup.shadow").version("9.4.0")
}

buildscript {
    dependencies {
        classpath("org.yaml:snakeyaml:2.0")
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":implementation:v1_18_R2"))
    implementation(project(":implementation:v1_19_R3"))
    implementation(project(":implementation:v1_20_R1"))
    implementation(project(":implementation:v1_20_R2"))
    implementation(project(":implementation:v1_20_R3"))
    implementation(project(":implementation:v1_20_R4"))
    implementation(project(":implementation:v1_21_R1"))
    implementation(project(":implementation:v1_21_R2"))
    implementation(project(":implementation:v1_21_R3"))
    implementation(project(":implementation:v1_21_R4"))
    implementation(project(":implementation:v1_21_R5"))
    implementation(project(":implementation:v1_21_R6"))
    implementation(project(":implementation:v1_21_R7"))
    implementation(project(":implementation:v26_1"))
    implementation(project(":implementation:v26_2"))
    implementation("com.github.AvarionMC:yaml:1.1.7")
}

val pluginConfig = file("${rootProject.projectDir}/common/src/main/resources/plugin.yml").inputStream().use {
    org.yaml.snakeyaml.Yaml().load<Map<String, Any>>(it)
}

tasks.shadowJar {
    //This will break all versions before 1.21.9.
    // Can't do much about that.
    manifest {
        attributes["paperweight-mappings-namespace"] = "mojang"
    }

    archiveBaseName.set(pluginConfig["name"].toString())
    archiveVersion.set(pluginConfig["version"].toString())
    archiveClassifier.set("")

    exclude("META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA")

    relocate("io.papermc.lib", "org.terraform.lib")
}

tasks.register<Copy>("deploy") {
    dependsOn(tasks.named("shadowJar"))

    from(layout.buildDirectory.dir("libs"))
    include("*.jar")
    into(rootProject.projectDir)

    doNotTrackState("Disable state tracking due to file access issues")
}
