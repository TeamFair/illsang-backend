springBoot {
    mainClass.set("com.teamfair.modulebootstrap")
}

dependencies {
    implementation(project(":module-common"))
    implementation(project(":module-management"))
    implementation(project(":module-quest"))
    implementation(project(":module-query"))
    implementation(project(":module-user"))
}