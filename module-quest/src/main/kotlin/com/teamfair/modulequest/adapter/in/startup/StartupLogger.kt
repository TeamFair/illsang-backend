package com.teamfair.modulequest.adapter.`in`.startup

import jakarta.annotation.PostConstruct
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Slf4j
@Component(value = "QuestStartupLogger")
class StartupLogger {
    private val logger = LoggerFactory.getLogger(StartupLogger::class.java)

    @PostConstruct
    fun init() {
        logger.info("[module-quest] StartupLogger initialized")
    }
}
