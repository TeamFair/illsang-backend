package com.teamfair.modulequest.adapter.out.persistence.repository

import com.teamfair.modulequest.adapter.out.persistence.entity.MissionEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MissionRepository : JpaRepository<MissionEntity, Long> {
    fun findByQuestId(questId: Long): List<MissionEntity>
}