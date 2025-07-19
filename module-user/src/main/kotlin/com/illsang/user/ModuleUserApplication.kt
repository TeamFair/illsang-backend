package com.illsang.user

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.illsang.user", "com.illsang.common", "com.illsang.auth"])
class ModuleUserApplication

fun main(args: Array<String>) {
    runApplication<ModuleUserApplication>(*args)
}
