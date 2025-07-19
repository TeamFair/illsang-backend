package com.illsang.management.service

import com.illsang.management.domain.model.ImageModel
import com.illsang.management.dto.request.ImageCreateRequest
import com.illsang.management.domain.entity.ImageEntity
import com.illsang.management.enums.ImageType
import com.illsang.management.repository.ImageRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

@Service
@Transactional(readOnly = true)
class ImageService(
    private val imageRepository: ImageRepository,
    private val s3Service: S3Service,
) {

    @Transactional
    fun createImage(request: ImageCreateRequest): ImageModel {
        val fileName = this.generateImageFileName(request.type)

        val image = request.toEntity(fileName)
        this.imageRepository.save(image)

        this.s3Service.uploadImage(
            fileName = fileName,
            file=request.file,
        )

        return ImageModel.from(image)
    }

    fun getImagesByType(type: ImageType): List<ImageModel> {
        return this.imageRepository.findByType(type).map { ImageModel.from(it) }
    }

    fun downloadImage(fileName: String): ByteArray {
        return this.s3Service.downloadImage(fileName)
    }

    @Transactional
    fun deleteImage(id: String) {
        val image = this.findById(id)

        this.imageRepository.delete(image)
        this.s3Service.deleteImage(id)
    }

    fun existOrThrowImage(imageId: String) {
        this.findById(imageId)
    }

    private fun findById(id: String): ImageEntity =
        this.imageRepository.findByIdOrNull(id)
            ?: throw IllegalArgumentException("Image not found with id: $id")

    private fun generateImageFileName(type: ImageType): String {
        val timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
        val random = String.format("%02d", Random.nextInt(0, 100))
        return "IM${type.prefix}/${timestamp}${random}"
    }

}
