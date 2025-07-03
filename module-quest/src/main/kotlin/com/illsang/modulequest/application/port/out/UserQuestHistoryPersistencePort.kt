package com.illsang.modulequest.application.port.out

import com.illsang.modulequest.domain.model.UserQuestHistoryModel

interface UserQuestHistoryPersistencePort {
    fun save(userQuestHistoryModel: UserQuestHistoryModel): UserQuestHistoryModel
    fun findById(id: Long): UserQuestHistoryModel?
    fun findAll(): List<UserQuestHistoryModel>
    fun findByUserId(userId: Long): List<UserQuestHistoryModel>
    fun findByQuestId(questId: Long): List<UserQuestHistoryModel>
    fun deleteById(id: Long)
    fun existsById(id: Long): Boolean
}
