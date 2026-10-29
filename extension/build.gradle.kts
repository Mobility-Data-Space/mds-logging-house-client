plugins {
    `java-library`
    `maven-publish`
}

dependencies {
    implementation(libs.edc.control.plane.spi)
    implementation(libs.edc.core.spi)

    implementation(libs.edc.core.lib)

    implementation(libs.okhttp)
    implementation(libs.json)
    implementation(libs.jackson.datatype.jsr310)

    implementation(libs.jersey.multipart)

    implementation(libs.infomodel.java)
    implementation(libs.infomodel.util)

    implementation(libs.postgresql)
    implementation(libs.flyway)
}

java {
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>(project.name) {
            from(components["java"])
            groupId = "mds-logging-house"
            artifactId = "client"
        }
    }
}
