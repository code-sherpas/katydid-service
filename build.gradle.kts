import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// PLUGINS -- BEGIN
plugins {
    kotlin("jvm") version "1.4.30"
    `java-library`
    jacoco
    id("org.sonarqube") version "3.1.1"
    id("com.diffplug.spotless") version "5.9.0"
    id("org.springframework.boot") version "2.4.2"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("plugin.spring") version "1.4.30"
}

allprojects {
    apply(plugin = "kotlin")
}
// PLUGINS -- END

// JAVA VERSION -- BEGIN
allprojects {
    java.sourceCompatibility = JavaVersion.VERSION_11

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
}
// JAVA VERSION -- END

// NULLABILITY -- BEGIN
allprojects {
    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }
}
// NULLABILITY -- END

// SPOTLESS -- BEGIN
allprojects {
    apply(plugin = "com.diffplug.spotless")

    spotless {
        kotlin {
            ktlint()
        }
        kotlinGradle {
            ktlint()
        }
    }

    listOf(tasks.compileJava, tasks.compileKotlin, tasks.compileTestJava, tasks.compileTestKotlin).forEach {
        it.get().mustRunAfter(tasks.spotlessCheck)
    }

    tasks.check {
        dependsOn(tasks.spotlessCheck)
    }
}
// SPOTLESS -- END

// SOURCES -- BEGIN
allprojects {
    java {
        withSourcesJar()
    }
}
// SOURCES -- END

// JAVADOC -- BEGIN
allprojects {
    java {
        withJavadocJar()
    }
}
// JAVADOC -- END

// JACOCO -- BEGIN
allprojects {
    apply(plugin = "jacoco")

    jacoco {
        toolVersion = "0.8.6"
    }

    tasks.jacocoTestReport {
        reports {
            xml.isEnabled = true
            html.isEnabled = true
        }

        dependsOn(tasks.test)
    }

    tasks.build {
        dependsOn(tasks.jacocoTestReport)
    }
}
// JACOCO -- END

// TEST LOGGING -- BEGIN
allprojects {
    tasks.withType<Test> {
        testLogging {
            showStandardStreams = false
            events("skipped", "failed")
            showExceptions = true
            showCauses = true
            showStackTraces = true
            exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        }
        afterSuite(printTestResult)
    }
}

val printTestResult: KotlinClosure2<TestDescriptor, TestResult, Void>
    get() = KotlinClosure2({ desc, result ->

        if (desc.parent == null) { // will match the outermost suite
            println("------")
            println(
                    "Results: ${result.resultType} (${result.testCount} tests, ${result.successfulTestCount} " +
                            "successes, ${result.failedTestCount} failures, ${result.skippedTestCount} skipped)"
            )
            println(
                    "Tests took: ${result.endTime - result.startTime} ms."
            )
            println("------")
        }
        null
    })
// TEST LOGGING -- END

// JUNIT -- BEGIN
allprojects {
    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
// JUNIT -- END

// Dependencies -- BEGIN
allprojects {
    repositories {
        mavenCentral()
        jcenter()
        maven { setUrl("https://dl.bintray.com/arrow-kt/arrow-kt/") }
    }

    dependencies {
        implementation(platform(kotlin("bom")))
        implementation(kotlin("stdlib-jdk8"))
        implementation(kotlin("reflect"))
        implementation("javax.inject:javax.inject:1")
        implementation(platform("io.arrow-kt:arrow-stack:0.11.0"))

        implementation("io.arrow-kt:arrow-core")
        implementation("io.arrow-kt:arrow-fx")
        implementation("io.arrow-kt:arrow-syntax")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.1")
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.1")
        testImplementation("org.junit.jupiter:junit-jupiter-params:5.7.1")
        testImplementation("io.mockk:mockk:1.10.5")
        testImplementation("org.assertj:assertj-core:3.19.0")
        testImplementation("com.github.tomakehurst:wiremock-jre8:2.27.2")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
}
// Dependencies -- END

// #####################################################################################################################

// SonarQube -- BEGIN
sonarqube {
    properties {
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.organization", "quipalup")
        property("sonar.projectKey", "quipalup-org_katydid-service")
    }
}
// SonarQube -- END

// CONTAINER TEST CONFIGURATION -- BEGIN
val containerTestName: String = "containerTest"
val containerTestSourceSetName: String = "containerTest"

sourceSets {
    create(containerTestSourceSetName) {
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output
        compileClasspath += sourceSets.test.get().output
        runtimeClasspath += sourceSets.test.get().output
    }
}

val containerTestImplementation: Configuration by configurations.getting {
    extendsFrom(configurations.implementation.get())
}

val containerTestRuntimeOnlyConfigurationName: String = "containerTestRuntimeOnly"
val containerTestRuntimeOnly: Configuration =
    configurations[containerTestRuntimeOnlyConfigurationName].extendsFrom(configurations.runtimeOnly.get())

val containerTest: Test = task<Test>(containerTestName) {
    description = "Runs container tests."
    group = "verification"
    testClassesDirs = sourceSets[containerTestSourceSetName].output.classesDirs
    classpath = sourceSets[containerTestSourceSetName].runtimeClasspath
}

tasks.check { dependsOn(containerTest) }

dependencies {
    containerTestImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage")
        exclude(group = "io.rest-assured")
        exclude(module = "mockito-core")
    }

    containerTestImplementation("com.ninja-squad:springmockk:3.0.1")

    containerTestImplementation("io.rest-assured:rest-assured:4.3.3")
    containerTestImplementation("io.rest-assured:json-path:4.3.3")
    containerTestImplementation("io.rest-assured:xml-path:4.3.3")
    containerTestImplementation("io.rest-assured:kotlin-extensions:4.3.3")
}
// CONTAINER TEST CONFIGURATION -- END
