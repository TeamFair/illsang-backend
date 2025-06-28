package com.teamfair.modulequest.adapter.out.persistence

import com.teamfair.modulequest.adapter.out.persistence.entity.QuizEntity
import com.teamfair.modulequest.adapter.out.persistence.entity.UserMissionHistoryEntity
import com.teamfair.modulequest.adapter.out.persistence.entity.UserQuizHistoryEntity
import com.teamfair.modulequest.adapter.out.persistence.repository.QuizRepository
import com.teamfair.modulequest.adapter.out.persistence.repository.UserMissionHistoryRepository
import com.teamfair.modulequest.adapter.out.persistence.repository.UserQuizHistoryRepository
import com.teamfair.modulequest.application.port.out.UserQuizHistoryPersistencePort
import com.teamfair.modulequest.domain.mapper.UserQuizHistoryMapper
import com.teamfair.modulequest.domain.model.UserQuizHistory
import org.springframework.stereotype.Component

@Component
class UserQuizHistoryPersistenceAdapter(
    private val userQuizHistoryRepository: UserQuizHistoryRepository,
    private val quizRepository: QuizRepository,
    private val userMissionHistoryRepository: UserMissionHistoryRepository
) : UserQuizHistoryPersistencePort {

    override fun save(userQuizHistory: UserQuizHistory): UserQuizHistory {
        val quizEntity = quizRepository.findById(userQuizHistory.quizId)
            .orElseThrow { IllegalArgumentException("Quiz not found with id: ${userQuizHistory.quizId}") }
        
        val userMissionHistoryEntity = userMissionHistoryRepository.findById(userQuizHistory.userMissionHistoryId)
            .orElseThrow { IllegalArgumentException("UserMissionHistory not found with id: ${userQuizHistory.userMissionHistoryId}") }
        
        val entity = UserQuizHistoryMapper.toEntity(userQuizHistory, quizEntity, userMissionHistoryEntity)
        val savedEntity = userQuizHistoryRepository.save(entity)
        return UserQuizHistoryMapper.toModel(savedEntity)
    }

    override fun findById(id: Long): UserQuizHistory? {
        return userQuizHistoryRepository.findById(id)
            .map { UserQuizHistoryMapper.toModel(it) }
            .orElse(null)
    }

    override fun findAll(): List<UserQuizHistory> {
        return userQuizHistoryRepository.findAll()
            .map { UserQuizHistoryMapper.toModel(it) }
    }

    override fun findByUserId(userId: Long): List<UserQuizHistory> {
        return userQuizHistoryRepository.findByUserId(userId)
            .map { UserQuizHistoryMapper.toModel(it) }
    }

    override fun findByQuizId(quizId: Long): List<UserQuizHistory> {
        return userQuizHistoryRepository.findByQuizId(quizId)
            .map { UserQuizHistoryMapper.toModel(it) }
    }

    override fun findByUserMissionHistoryId(userMissionHistoryId: Long): List<UserQuizHistory> {
        return userQuizHistoryRepository.findByUserMissionHistoryId(userMissionHistoryId)
            .map { UserQuizHistoryMapper.toModel(it) }
    }

    override fun deleteById(id: Long) {
        userQuizHistoryRepository.deleteById(id)
    }

    override fun existsById(id: Long): Boolean {
        return userQuizHistoryRepository.existsById(id)
    }
} 