package com.illsang.management.adapter.out.persistence.repository

import com.illsang.management.adapter.out.persistence.entity.ImageEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ImageRepository : JpaRepository<ImageEntity, Long> {
    fun findByType(type: String): List<ImageEntity>
} 