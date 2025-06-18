pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://maven.pkg.github.com/kingsleyadio/android_commons") {
            credentials.username = prop("GITHUB_ACTOR")
            credentials.password = prop("GITHUB_TOKEN")
        }
    }
}

fun prop(key: String, fallback: String = ""): String {
    return providers.gradleProperty(key)
            .orElse(providers.environmentVariable(key))
            .orNull
            ?: fallback.ifEmpty { error("$key not found") }
}

rootProject.name = "BahdTech"

include("app")
