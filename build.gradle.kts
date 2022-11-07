plugins {
    id("java")
    id("org.springframework.boot") version ("2.7.3")
    id("io.spring.dependency-management") version ("1.0.11.RELEASE")
}

group = "org.icloud"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

subprojects {

    repositories {
        mavenCentral()
    }

    apply {
        plugin("java")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-amqp")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.springframework.amqp:spring-rabbit-test")
        implementation("com.mysql:mysql-connector-j:8.0.31")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.boot:spring-boot-starter-web")
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
        implementation("org.projectlombok:lombok")
        testImplementation("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok")
        testAnnotationProcessor("org.projectlombok:lombok")
    }
}


tasks.getByName<Test>("test") {
    useJUnitPlatform()
}