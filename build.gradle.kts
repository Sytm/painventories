import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "de.md5lukas"
version = "1.0.0-SNAPSHOT"
description = "PainVentories"

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()

    maven {
        url = uri("https://repo.sytm.de/repository/maven-hosted/")
    }

    maven {
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }

    maven {
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("de.md5lukas:md5-commons:2.0.0-SNAPSHOT")
    compileOnly("org.jetbrains:annotations:20.1.0")
    compileOnly("org.spigotmc:spigot-api:1.13.2-R0.1-SNAPSHOT")
}

tasks.withType<ProcessResources> {
    expand("version" to project.version)
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<ShadowJar> {
    archiveClassifier.set("")
    minimize()

    dependencies {
        include(dependency("de.md5lukas:md5-commons"))
    }

    relocate("de.md5lukas.commons", "de.md5lukas.painventories.internal.commons")
}

publishing {
    repositories {
        maven {
            val releasesRepoUrl = "https://repo.sytm.de/repository/maven-releases/"
            val snapshotsRepoUrl = "https://repo.sytm.de/repository/maven-snapshots/"
            url = uri(if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl)
            credentials {
                if (project.hasProperty("mavenUsername")) {
                    username = project.properties["mavenUsername"] as String
                }
                if (project.hasProperty("mavenPassword")) {
                    password = project.properties["mavenPassword"] as String
                }
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            project.shadow.component(this)
        }
    }
}
