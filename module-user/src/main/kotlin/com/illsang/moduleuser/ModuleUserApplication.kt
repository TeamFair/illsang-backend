package com.illsang.moduleuser

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.illsang.moduleuser", "com.illsang.common"])
class ModuleUserApplication

fun main(args: Array<String>) {
    runApplication<ModuleUserApplication>(*args)
}
