package com.illsang.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import software.amazon.awssdk.services.s3.S3Client

@Configuration
@Profile("!local&&!test") // Disable AWS beans for local profile
class AWSConfig {
    @Bean
    fun amazonS3Client(): S3Client? {
        return S3Client.create()
    }
}
