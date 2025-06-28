package com.teamfair.modulequest.application.port.out

import com.teamfair.modulequest.domain.model.QuestReward

interface QuestRewardPersistencePort {
    fun save(questReward: QuestReward): QuestReward
    fun findById(id: Long): QuestReward?
    fun findAll(): List<QuestReward>
    fun findByQuestId(questId: Long): List<QuestReward>
    fun deleteById(id: Long)
    fun existsById(id: Long): Boolean
} 