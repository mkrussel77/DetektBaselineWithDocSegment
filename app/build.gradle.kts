plugins {
    kotlin("jvm")
    id("io.gitlab.arturbosch.detekt")
}

dependencies {
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.19.0")
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-cli:1.19.0")
    detekt(project(":detekt-extensions"))
}

detekt {
    config = files("${project.rootDir}/quality/detekt/detekt-config.yml")
}