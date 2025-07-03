package com.illsang.modulequest.adapter.out.persistence.repository

import com.illsang.modulequest.adapter.out.persistence.entity.QuestRewardEntity
import org.springframework.data.jpa.repository.JpaRepository

interface QuestRewardRepository : JpaRepository<QuestRewardEntity, Long> {
    fun findByQuestId(questId: Long): List<QuestRewardEntity>
}
