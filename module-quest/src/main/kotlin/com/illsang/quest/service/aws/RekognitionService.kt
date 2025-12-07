package com.illsang.quest.service.aws

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.rekognition.RekognitionClient
import software.amazon.awssdk.services.rekognition.model.*

@Service
class RekognitionService(
    private val rekognitionClient: RekognitionClient,
    @param:Value("\${cloud.bucket.name}") private val bucketName: String,
) {

    fun detectLabels(
        sourceImage: String,
        includeLabels: List<String>,
        minConfidence: Float = 60.0f,
    ): List<Pair<String, Float>> {
        val s3Object = S3Object.builder()
            .bucket(bucketName)
            .name(sourceImage)
            .build()
        val image = Image.builder()
            .s3Object(s3Object)
            .build()
        val request = DetectLabelsRequest.builder()
            .features(DetectLabelsFeatureName.GENERAL_LABELS)
            .image(image)
            .settings(
                DetectLabelsSettings.builder()
                    .generalLabels(
                        GeneralLabelsSettings.builder()
                            .labelInclusionFilters(includeLabels)
                            .build()
                    )
                .build()
            )
            .minConfidence(minConfidence)
            .build()

        val response = rekognitionClient.detectLabels(request)
        return response.labels().map { Pair(it.name(), it.confidence()) }
    }

    fun detectModerationLabels(
        sourceImage: String,
        minConfidence: Float = 0.0f,
    ): List<Pair<String, Float>> {
        val s3Object = S3Object.builder()
            .bucket(bucketName)
            .name(sourceImage)
            .build()
        val image = Image.builder()
            .s3Object(s3Object)
            .build()

        val request = DetectModerationLabelsRequest.builder()
            .image(image)
            .minConfidence(minConfidence)
            .build()

        val response = rekognitionClient.detectModerationLabels(request)
        return response.moderationLabels().map { Pair(it.name(), it.confidence()) }
    }
}
