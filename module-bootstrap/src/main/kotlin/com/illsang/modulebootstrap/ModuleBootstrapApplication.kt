package com.illsang.modulebootstrap

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.illsang"])
class ModuleBootstrapApplication

fun main(args: Array<String>) {
    runApplication<ModuleBootstrapApplication>(*args)
}
