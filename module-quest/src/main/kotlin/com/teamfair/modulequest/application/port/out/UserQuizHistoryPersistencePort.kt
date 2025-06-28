package com.teamfair.modulequest.application.port.out

import com.teamfair.modulequest.domain.model.UserQuizHistory

interface UserQuizHistoryPersistencePort {
    fun save(userQuizHistory: UserQuizHistory): UserQuizHistory
    fun findById(id: Long): UserQuizHistory?
    fun findAll(): List<UserQuizHistory>
    fun findByUserId(userId: Long): List<UserQuizHistory>
    fun findByQuizId(quizId: Long): List<UserQuizHistory>
    fun findByUserMissionHistoryId(userMissionHistoryId: Long): List<UserQuizHistory>
    fun deleteById(id: Long)
    fun existsById(id: Long): Boolean
} 