package com.illsang.modulequest.application.port.out

import com.illsang.modulequest.domain.model.UserQuizHistoryModel

interface UserQuizHistoryPersistencePort {
    fun save(userQuizHistoryModel: UserQuizHistoryModel): UserQuizHistoryModel
    fun findById(id: Long): UserQuizHistoryModel?
    fun findAll(): List<UserQuizHistoryModel>
    fun findByUserId(userId: Long): List<UserQuizHistoryModel>
    fun findByQuizId(quizId: Long): List<UserQuizHistoryModel>
    fun findByUserMissionHistoryId(userMissionHistoryId: Long): List<UserQuizHistoryModel>
    fun deleteById(id: Long)
    fun existsById(id: Long): Boolean
}
