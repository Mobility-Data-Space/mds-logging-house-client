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

    implementation(project(":extension"))
}

application {
    mainClass.set("org.eclipse.edc.boot.system.runtime.BaseRuntime")
}
