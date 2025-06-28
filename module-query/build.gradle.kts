springBoot {
    mainClass.set("com.teamfair.modulequery")
}

dependencies {
    implementation(project(":module-common"))
    implementation(project(":module-management"))
    implementation(project(":module-quest"))
    implementation(project(":module-user"))
}