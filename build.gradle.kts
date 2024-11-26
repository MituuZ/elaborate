plugins {
    kotlin("jvm") version "2.0.21"
    application
}

group = "com.mituuz"
version = "0.1.1"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.11.3")
    implementation("org.slf4j:slf4j-api:2.0.16")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("com.mituuz.elaborate.Elaborate")
}
