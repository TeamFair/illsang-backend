package com.illsang.management.repository

import com.illsang.common.enums.TitleType
import com.illsang.management.domain.entity.TitleEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TitleRepository : JpaRepository<TitleEntity, String> {
    fun findAllByTypeOrderById(type: TitleType): List<TitleEntity>
}