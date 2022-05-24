import fr.brouillard.oss.jgitver.GitVersionCalculator
import fr.brouillard.oss.jgitver.Strategies
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    dependencies {
        classpath(group = "fr.brouillard.oss", name = "jgitver", version = "0.14.+")
    }
}

plugins {
    kotlin("jvm") version "1.6.21"
    `maven-publish`
}

group = "dev.su5ed"
version = getGitVersion()

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
        register<MavenPublication>(project.name) {
            from(components["java"])

            pom {
                name.set(project.name)
                description.set("Java bytecode assembler as a Kotlin DSL (Su5eD's fork)")
                url.set("https://github.com/Su5eD/Koffee")

                scm {
                    url.set("https://github.com/Su5eD/Koffee")
                    connection.set("scm:git:git://github.com/Su5eD/Koffee")
                    developerConnection.set("scm:git:git@github.com:Su5eD/Koffee.git")
                }

                issueManagement {
                    system.set("github")
                    url.set("https://github.com/Su5eD/Koffee/issues")
                }

                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://github.com/Su5eD/Koffee/blob/master/LICENSE.txt")
                        distribution.set("repo")
                    }
                }

                developers {
                    developer {
                        id.set("videogame-hacker")
                        name.set("Charlotte Som")
                    }

                    developer {
                        id.set("su5ed")
                        name.set("Su5eD")
                    }
                }
            }
        }
    }

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

fun getGitVersion(): String {
    val jgitver = GitVersionCalculator.location(rootDir)
        .setNonQualifierBranches("master")
        .setStrategy(Strategies.SCRIPT)
        .setScript("print \"\${metadata.CURRENT_VERSION_MAJOR};\${metadata.CURRENT_VERSION_MINOR};\${metadata.CURRENT_VERSION_PATCH + metadata.COMMIT_DISTANCE}\"")
    return jgitver.version
}
