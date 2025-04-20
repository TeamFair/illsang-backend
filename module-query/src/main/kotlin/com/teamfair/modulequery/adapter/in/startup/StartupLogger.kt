package com.teamfair.modulequery.adapter.`in`.startup

import com.teamfair.modulemanagement.adapter.`in`.startup.StartupLogger
import jakarta.annotation.PostConstruct
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Slf4j
@Component(value = "QueryStartupLogger")
class StartupLogger {
    private val logger = LoggerFactory.getLogger(StartupLogger::class.java)

    @PostConstruct
    fun init() {
        logger.info("[module-query] StartupLogger initialized")
    }
}
