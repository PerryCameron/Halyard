plugins {
    application
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("org.beryx.jlink") version "2.26.0"
}

group = "org.ecsail"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

javafx {
    version = "21"
    modules = listOf("javafx.controls")
}

application {
    mainClass.set("org.ecsail.BaseApplication")
    mainModule.set("Halyard")
    applicationDefaultJvmArgs = listOf(
            "-Djavafx.macosx.embedded=true",
            "-Dprism.order=sw",
            "-Dglass.accessible.force=false",
            "-Dapple.awt.application.appearance=system",
            "-Xdock:name=Halyard"  // Add this line
    )
}

dependencies {
    implementation("org.slf4j:slf4j-api:2.0.5")
    implementation("ch.qos.logback:logback-classic:1.5.6")
    implementation("ch.qos.logback:logback-core:1.5.6")
    implementation("org.apache.logging.log4j:log4j-to-slf4j:2.20.0")
    implementation("org.slf4j:jcl-over-slf4j:2.0.5")
    implementation("com.github.mwiede:jsch:0.2.7")
    implementation("org.mariadb.jdbc:mariadb-java-client:3.2.0")
    implementation("org.springframework:spring-jdbc:6.1.8")
    implementation("com.itextpdf:itext7-core:7.2.6")
    implementation("com.itextpdf:layout:7.2.6")
}

jlink {
    options = listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages")
    launcher {
        name = "Halyard"
    }
}

sourceSets {
    named("main") {
        java {
            setSrcDirs(listOf("src/main/java"))
        }
        resources {
            setSrcDirs(listOf("src/main/resources"))
        }
    }
}

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

tasks.withType<Copy> {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

tasks.withType<JavaCompile> {
    options.compilerArgs.addAll(listOf("-Xlint:deprecation", "-Xlint:unchecked"))
}
