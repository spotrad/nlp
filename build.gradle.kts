import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.3.72"
    //application
    id("com.github.johnrengelman.shadow") version "6.0.0"
}

group = "com.implauzable.npl"
version = "0.1"

repositories {
    mavenCentral()
    maven("https://dl.bintray.com/arrow-kt/arrow-kt/")
    maven("https://oss.jfrog.org/artifactory/oss-snapshot-local/")
}
val arrowVersion: String by project
val ktorVersion: String by project

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.arrow-kt:arrow-core:$arrowVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    testImplementation("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}


tasks.withType<ShadowJar> {
    manifest {
        attributes["Main-Class"] = "npl.AppKt"
    }
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    freeCompilerArgs = listOf("-Xinline-classes")
}