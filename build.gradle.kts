import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    `maven-publish`
}

group = "codes.som"
version = "8.0.2"

repositories {
    mavenCentral()
}

dependencies {
    arrayOf("asm", "asm-tree", "asm-commons").forEach {
        implementation("org.ow2.asm:$it:9.2")
    }

    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.2")
}

java {
    withSourcesJar()
}

kotlin {
    explicitApi()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }

    repositories {
        repositories {
            val mavenUser = System.getenv("GOFANCY_MAVEN_USER")
            val mavenToken = System.getenv("GOFANCY_MAVEN_TOKEN")

            if (mavenUser != null && mavenToken != null) {
                maven {
                    name = "gofancy"
                    url = uri("https://maven.gofancy.wtf/releases")

                    credentials {
                        username = mavenUser
                        password = mavenToken
                    }
                }
            }
        }
    }
}
