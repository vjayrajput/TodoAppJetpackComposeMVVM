pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Todo App"
include(":app")
include(":business:di")
include(":business:data:di")
include(":business:data:entity")
include(":business:data:main")
include(":business:domain:di")
include(":business:domain:main")
include(":business:domain:model")
include(":common:domain:api")
include(":common:general:extensions")
include(":common:general:models")
include(":common:navigation")
include(":common:ui:resources:drawables")
include(":common:ui:resources:strings")
include(":common:ui:theme")
