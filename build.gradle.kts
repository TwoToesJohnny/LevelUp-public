plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation ("com.zaxxer:HikariCP:5.0.1")
    implementation ("com.mysql:mysql-connector-j:8.2.0")
    implementation ("org.springframework.security:spring-security-crypto:6.4.4")
    implementation("ch.qos.logback:logback-classic:1.4.11")
}

tasks.test {
    useJUnitPlatform()
}