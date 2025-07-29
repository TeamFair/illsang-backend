springBoot {
    mainClass.set("com.illsang.quest")
}

dependencies {
    implementation(project(":module-common"))
    implementation(project(":module-auth"))
}
