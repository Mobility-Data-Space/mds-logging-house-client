# Clients

## Java EDC Extension

### Integrating with Maven Repository

To use the Maven package from our repository, update your `build.gradle.kts` with the following repository configuration:

```kotlin
repositories {
    mavenCentral()
    maven {
        url = uri("https://maven.pkg.github.com/Mobility-Data-Space/mds-logging-house-client")
        credentials {
            username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
            password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
        }
    }
}
```

This configuration includes the necessary Maven repository for our package and sets up authentication using either project properties or environment variables.

### Setting Up Authentication

To access our public Maven repository, authentication is required. Please follow the detailed [GitHub Packages with Apache Maven documentation](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-apache-maven-registry#authenticating-to-github-packages) for guidance on setting up authentication.

### Adding the Dependency

Once the repository is added and authentication is configured, you can include the package in your dependencies:

```kotlin
dependencies {
    implementation("mds-logging-house:client")
}
```

### Environment Configuration

The `logging-house-client` relies on two key environment variables for configuration:

| Name                                        | Required | Default   | Description                                                                   |
|---------------------------------------------|----------|-----------|-------------------------------------------------------------------------------|
| `EDC_LOGGINGHOUSE_EXTENSION_ENABLED`        | no       | `false`   | Set to `true` to enable the extension, or `false` to disable it               |
| `EDC_LOGGINGHOUSE_EXTENSION_URL`            | yes      | `none`    | Specify the URL of the Logging-House-Server (e.g., `clearing.demo.truzzt.eu`) |
| `EDC_LOGGINGHOUSE_EXTENSION_FLYWAY_REPAIR`  | no       | `false`   | Enable the flyway repair command on extension startup                         |
| `EDC_LOGGINGHOUSE_EXTENSION_FLYWAY_CLEAN`   | no       | `false`   | Enable the flyway clean command on extension startup                          |
| `EDC_LOGGINGHOUSE_EXTENSION_WORKERS_MAX`    | no       | `1`       | Specify the maximum number of workers created to parallel processing          |
| `EDC_LOGGINGHOUSE_EXTENSION_WORKERS_DELAY`  | no       | `30`      | Specify the delay, in seconds, before the first workers execution             |
| `EDC_LOGGINGHOUSE_EXTENSION_WORKERS_PERIOD` | no       | `10`      | Specify the period, in seconds, between each workers execution                |

Ensure these environment variables are set as per your requirements for optimal functionality of the client.
