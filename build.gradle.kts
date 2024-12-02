plugins {
    kotlin("jvm") version "2.1.0"
}

kotlin {
    jvmToolchain(23) // you can try other versions here
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}