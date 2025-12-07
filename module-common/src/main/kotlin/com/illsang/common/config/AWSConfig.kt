package com.illsang.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.services.rekognition.RekognitionClient
import software.amazon.awssdk.services.s3.S3Client

@Configuration
class AWSConfig {
    @Bean
    fun amazonS3Client(): S3Client? {
        return S3Client.create()
    }

    @Bean
    fun amazonRekognitionClient(): RekognitionClient? {
        return RekognitionClient.create()
    }
}
