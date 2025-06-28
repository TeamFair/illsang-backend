package com.teamfair.modulequest.adapter.out.persistence.repository

import com.teamfair.modulequest.adapter.out.persistence.entity.QuestEntity
import org.springframework.data.jpa.repository.JpaRepository

interface QuestRepository : JpaRepository<QuestEntity, Long> {
    fun findByType(type: String): List<QuestEntity>
    fun findByPopularYn(popularYn: Boolean): List<QuestEntity>
}