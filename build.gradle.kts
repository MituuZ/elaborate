plugins {
    kotlin("jvm") version "2.0.21"
    application
}

group = "com.mituuz"
version = "0.1.0"

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

tasks.register("copyStyles") {
    doLast {
        copy {
            from("src/main/resources/static/styles.css")
            into("build/static")
        }
    }
}

tasks.jar {
    from("src/main/resources/static") {
        include("styles.css")
        into("static")
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }
}

tasks.named("run") {
    dependsOn("copyStyles")
}

application {
    mainClass.set("com.mituuz.elaborate.Elaborate")
}
