package com.illsang.management.adapter.out.persistence

import com.illsang.management.adapter.out.persistence.repository.ImageRepository
import com.illsang.management.application.port.out.ImagePersistencePort
import com.illsang.management.domain.mapper.ImageMapper
import com.illsang.management.domain.model.ImageModel
import org.springframework.stereotype.Component

@Component
class ImagePersistenceAdapter(
    private val imageRepository: ImageRepository,
    private val imageMapper: ImageMapper
) : ImagePersistencePort {

    /**
     * save image
     */
    override fun save(imageModel: ImageModel): ImageModel {
        val entity = imageMapper.toEntity(imageModel)
        val savedEntity = imageRepository.save(entity)
        return imageMapper.toModel(savedEntity)
    }

    /**
     * find image by id
     */
    override fun findById(id: Long): ImageModel? {
        return imageRepository.findById(id)
            .map { imageMapper.toModel(it) }
            .orElse(null)
    }

    /**
     * find all image
     */
    override fun findAll(): List<ImageModel> {
        return imageRepository.findAll()
            .map { imageMapper.toModel(it) }
    }

    /**
     * find all image by type
     */
    override fun findByType(type: String): List<ImageModel> {
        return imageRepository.findByType(type)
            .map { imageMapper.toModel(it) }
    }

    /**
     * delete image by id
     */
    override fun deleteById(id: Long) {
        imageRepository.deleteById(id)
    }

    /**
     * check image exists by id
     */
    override fun existsById(id: Long): Boolean {
        return imageRepository.existsById(id)
    }
} 