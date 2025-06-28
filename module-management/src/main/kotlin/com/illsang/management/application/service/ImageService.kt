package com.illsang.management.application.service

import com.illsang.management.application.command.CreateImageCommand
import com.illsang.management.application.command.UpdateImageCommand
import com.illsang.management.application.port.out.ImagePersistencePort
import com.illsang.management.domain.model.ImageModel
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ImageService(
    private val imagePersistencePort: ImagePersistencePort
) {
    /**
     * create image
     */
    @Transactional
    fun createImage(command: CreateImageCommand): ImageModel {
        val imageModel = ImageModel(
            type = command.type,
            name = command.name,
            size = command.size
        )
        imageModel.validate()
        return imagePersistencePort.save(imageModel)
    }

    /**
     * get image
     */
    fun getImage(id: Long): ImageModel {
        return imagePersistencePort.findById(id) 
            ?: throw IllegalArgumentException("Image not found with id: $id")
    }

    /**
     * get all images
     */
    fun getAllImages(): List<ImageModel> {
        return imagePersistencePort.findAll()
    }

    /**
     * get all images by type
     */
    fun getImagesByType(type: String): List<ImageModel> {
        return imagePersistencePort.findByType(type)
    }

    /**
     * update image
     */
    @Transactional
    fun updateImage(command: UpdateImageCommand): ImageModel {
        val existingImage = imagePersistencePort.findById(command.id)
            ?: throw IllegalArgumentException("Image not found with id: ${command.id}")
        
        val updatedImage = existingImage.copy(
            type = command.type ?: existingImage.type,
            name = command.name ?: existingImage.name,
            size = command.size ?: existingImage.size
        )
        updatedImage.validate()
        return imagePersistencePort.save(updatedImage)
    }

    /**
     * delete image
     */
    @Transactional
    fun deleteImage(id: Long) {
        when {
            !imagePersistencePort.existsById(id) -> {
                throw IllegalArgumentException("Image not found with id: $id")
            }
            else -> imagePersistencePort.deleteById(id)
        }
    }
} 