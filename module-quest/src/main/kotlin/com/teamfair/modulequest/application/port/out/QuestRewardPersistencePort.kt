package com.teamfair.modulequest.application.port.out

import com.teamfair.modulequest.domain.model.QuestRewardModel

interface QuestRewardPersistencePort {
    fun save(questRewardModel: QuestRewardModel): QuestRewardModel
    fun findById(id: Long): QuestRewardModel?
    fun findAll(): List<QuestRewardModel>
    fun findByQuestId(questId: Long): List<QuestRewardModel>
    fun deleteById(id: Long)
    fun existsById(id: Long): Boolean
} 