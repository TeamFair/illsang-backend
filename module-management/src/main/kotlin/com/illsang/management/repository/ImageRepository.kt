package com.illsang.management.repository

import com.illsang.management.domain.entity.ImageEntity
import com.illsang.management.enums.ImageType
import org.springframework.data.jpa.repository.JpaRepository

interface ImageRepository : JpaRepository<ImageEntity, String> {
    fun findByType(type: ImageType): List<ImageEntity>
}
