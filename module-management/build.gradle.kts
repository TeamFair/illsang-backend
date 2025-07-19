springBoot {
    mainClass.set("com.illsang.management")
}

dependencies {
    implementation(project(":module-common"))
    implementation(project(":module-auth"))
}
