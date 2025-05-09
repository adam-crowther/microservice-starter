plugins {
    id("java-conventions")
    id("spock-conventions")
}

dependencies {
    implementation(libs.lombok)
    implementation(libs.mapstruct)

    annotationProcessor(libs.lombok)
}
