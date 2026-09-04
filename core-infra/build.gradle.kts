plugins {
    id("java")
}

group = "com.paranoiax"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:4.0.7"))
    annotationProcessor(platform("org.springframework.boot:spring-boot-dependencies:4.0.7"))

    implementation(project(":core"))
    implementation("tools.jackson.core:jackson-databind:3.0.0")
    implementation("commons-codec:commons-codec:1.22.1")
    implementation("io.github.erdtman:java-json-canonicalization:1.1")

    implementation("org.springframework:spring-tx")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
}