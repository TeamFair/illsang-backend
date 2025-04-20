package com.teamfair.modulebootstrap

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.teamfair"])
class ModuleBootstrapApplication

fun main(args: Array<String>) {
    runApplication<ModuleBootstrapApplication>(*args)
}
