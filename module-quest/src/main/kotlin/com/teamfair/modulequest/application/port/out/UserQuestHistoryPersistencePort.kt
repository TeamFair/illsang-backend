package com.teamfair.modulequest.application.port.out

import com.teamfair.modulequest.domain.model.UserQuestHistory

interface UserQuestHistoryPersistencePort {
    fun save(userQuestHistory: UserQuestHistory): UserQuestHistory
    fun findById(id: Long): UserQuestHistory?
    fun findAll(): List<UserQuestHistory>
    fun findByUserId(userId: Long): List<UserQuestHistory>
    fun findByQuestId(questId: Long): List<UserQuestHistory>
    fun deleteById(id: Long)
    fun existsById(id: Long): Boolean
} 