package com.illsang.management

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.illsang.management", "com.illsang.common", "com.illsang.auth"])
class ModuleManagementApplication

fun main(args: Array<String>) {
	runApplication<ModuleManagementApplication>(*args)
}
