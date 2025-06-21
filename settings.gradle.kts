rootProject.name = "Rice"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}
include(":core")
include(":core")
include(":core:common")
include(":core:data")
include(":core:database")
include(":core:database")
include(":core:designsystem")
include(":core:domain")
include(":core:domain")
include(":core:network")
include(":data")

include(":feature")
include(":feature:food")
include(":composeApp")
include(":shared")
