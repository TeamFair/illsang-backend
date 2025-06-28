package com.illsang.management.application.port.out

import com.illsang.management.domain.model.ImageModel

interface ImagePersistencePort {
    fun save(imageModel: ImageModel): ImageModel
    fun findById(id: Long): ImageModel?
    fun findAll(): List<ImageModel>
    fun findByType(type: String): List<ImageModel>
    fun deleteById(id: Long)
    fun existsById(id: Long): Boolean
} 