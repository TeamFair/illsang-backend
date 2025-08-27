package com.illsang.modulebootstrap

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication(scanBasePackages = ["com.illsang"])
@EnableAsync
class ModuleBootstrapApplication

fun main(args: Array<String>) {
    runApplication<ModuleBootstrapApplication>(*args)
}
