package com.illsang.management.repository

import com.illsang.management.domain.entity.TitleEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TitleRepository : JpaRepository<TitleEntity, String> {
}