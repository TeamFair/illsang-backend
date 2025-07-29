package com.illsang.quest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.illsang.quest", "com.illsang.common", "com.illsang.auth"])
class ModuleQuestApplication

fun main(args: Array<String>) {
    runApplication<ModuleQuestApplication>(*args)
}
