pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
    }
}

rootProject.name = "Amazonaws"
include(":app")
include(":core")
include(":core:data")
include(":core:network")
include(":core:model")
include(":core:domain")
include(":feature")
include(":feature:teams")
include(":core:common")
include(":feature:matches")
include(":core:timeschedule")
include(":core:testing")
include(":core:navigation")
