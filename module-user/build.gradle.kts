springBoot {
    mainClass.set("com.illsang.moduleuser")
}

dependencies {
    implementation(project(":module-common"))
    implementation(project(":module-auth"))
}
