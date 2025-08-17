plugins {
    application
}

val edcGroup: String by project

dependencies {
    runtimeOnly(libs.edc.controlplane.base.bom) {
        exclude(group = edcGroup, module = "auth-tokenbased")
    }
    runtimeOnly(libs.edc.controlplane.feature.sql.bom)
    runtimeOnly(libs.edc.dataplane.base.bom)
    runtimeOnly(libs.edc.dataplane.feature.sql.bom)

    runtimeOnly(libs.edc.oauth2.daps)
    runtimeOnly(libs.edc.oauth2.service)

    runtimeOnly(libs.edc.vault.hashicorp)

    // Tractusx EDC migrations libraries
    runtimeOnly(libs.tractusx.edc.postgresql.migration)
    runtimeOnly(libs.tractusx.edc.data.plane.migration)
    runtimeOnly(libs.tractusx.edc.control.plane.migration)
    runtimeOnly(libs.tractusx.edc.retirement.evaluation.store.sql)

    implementation(project(":extension"))
}

application {
    mainClass.set("org.eclipse.edc.boot.system.runtime.BaseRuntime")
}
