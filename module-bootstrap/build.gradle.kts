springBoot {
    mainClass.set("com.illsang.modulebootstrap")
}

dependencies {
    implementation(project(":module-common"))
    implementation(project(":module-management"))
    implementation(project(":module-quest"))
    implementation(project(":module-user"))
}
