package com.teamfair.modulequest.adapter.out.persistence

import com.teamfair.modulequest.adapter.out.persistence.repository.QuizRepository
import com.teamfair.modulequest.adapter.out.persistence.repository.UserMissionHistoryRepository
import com.teamfair.modulequest.adapter.out.persistence.repository.UserQuizHistoryRepository
import com.teamfair.modulequest.application.port.out.UserQuizHistoryPersistencePort
import com.teamfair.modulequest.domain.mapper.UserQuizHistoryMapper
import com.teamfair.modulequest.domain.model.UserQuizHistoryModel
import org.springframework.stereotype.Component

@Component
class UserQuizHistoryPersistenceAdapter(
    private val userQuizHistoryRepository: UserQuizHistoryRepository,
    private val quizRepository: QuizRepository,
    private val userMissionHistoryRepository: UserMissionHistoryRepository
) : UserQuizHistoryPersistencePort {

    override fun save(userQuizHistoryModel: UserQuizHistoryModel): UserQuizHistoryModel {
        val quizEntity = quizRepository.findById(userQuizHistoryModel.quizId)
            .orElseThrow { IllegalArgumentException("Quiz not found with id: ${userQuizHistoryModel.quizId}") }
        
        val userMissionHistoryEntity = userMissionHistoryRepository.findById(userQuizHistoryModel.userMissionHistoryId)
            .orElseThrow { IllegalArgumentException("UserMissionHistory not found with id: ${userQuizHistoryModel.userMissionHistoryId}") }
        
        val entity = UserQuizHistoryMapper.toEntity(userQuizHistoryModel, quizEntity, userMissionHistoryEntity)
        val savedEntity = userQuizHistoryRepository.save(entity)
        return UserQuizHistoryMapper.toModel(savedEntity)
    }

    override fun findById(id: Long): UserQuizHistoryModel? {
        return userQuizHistoryRepository.findById(id)
            .map { UserQuizHistoryMapper.toModel(it) }
            .orElse(null)
    }

    override fun findAll(): List<UserQuizHistoryModel> {
        return userQuizHistoryRepository.findAll()
            .map { UserQuizHistoryMapper.toModel(it) }
    }

    override fun findByUserId(userId: Long): List<UserQuizHistoryModel> {
        return userQuizHistoryRepository.findByUserId(userId)
            .map { UserQuizHistoryMapper.toModel(it) }
    }

    override fun findByQuizId(quizId: Long): List<UserQuizHistoryModel> {
        return userQuizHistoryRepository.findByQuizId(quizId)
            .map { UserQuizHistoryMapper.toModel(it) }
    }

    override fun findByUserMissionHistoryId(userMissionHistoryId: Long): List<UserQuizHistoryModel> {
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