springBoot {
    mainClass.set("com.illsang.user")
}

dependencies {
    implementation(project(":module-common"))
    implementation(project(":module-auth"))
}
