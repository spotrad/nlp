import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.3.72"
    id("com.github.johnrengelman.shadow") version "6.0.0"
}

group = "com.implauzable.nlp"
version = "local"

repositories {
    mavenCentral()
    maven("https://dl.bintray.com/arrow-kt/arrow-kt/")
    maven("https://oss.jfrog.org/artifactory/oss-snapshot-local/")
}

val arrowVersion: String by project
val ktorVersion: String by project
val awsSdkVersion: String by project
val kotlinLoggingVersion: String by project
val logbackVersion: String by project
val cliktVersion: String by project

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    // Arrow
    implementation("io.arrow-kt:arrow-core:$arrowVersion")
    implementation("io.arrow-kt:arrow-fx:$arrowVersion")

    // Aws
    implementation("com.amazonaws:aws-java-sdk:$awsSdkVersion")

    // CLI
    implementation("com.github.ajalt:clikt:$cliktVersion")

    // Logging
    implementation("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    // Testing
    testImplementation("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}


tasks.withType<ShadowJar> {
    manifest {
        attributes["Main-Class"] = "nlp.AppKt"
    }
    isZip64 = true
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