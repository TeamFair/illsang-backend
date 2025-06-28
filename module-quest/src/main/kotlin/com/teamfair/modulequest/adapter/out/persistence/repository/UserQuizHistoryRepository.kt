package com.teamfair.modulequest.adapter.out.persistence.repository

import com.teamfair.modulequest.adapter.out.persistence.entity.UserQuizHistoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserQuizHistoryRepository : JpaRepository<UserQuizHistoryEntity, Long> {
    fun findByUserId(userId: Long): List<UserQuizHistoryEntity>
    fun findByQuizId(quizId: Long): List<UserQuizHistoryEntity>
    fun findByUserMissionHistoryId(userMissionHistoryId: Long): List<UserQuizHistoryEntity>
} 