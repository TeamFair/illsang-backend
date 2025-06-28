package com.teamfair.modulequest.adapter.out.persistence

import com.teamfair.modulequest.adapter.out.persistence.entity.MissionEntity
import com.teamfair.modulequest.adapter.out.persistence.entity.UserMissionHistoryEntity
import com.teamfair.modulequest.adapter.out.persistence.entity.UserQuestHistoryEntity
import com.teamfair.modulequest.adapter.out.persistence.repository.MissionRepository
import com.teamfair.modulequest.adapter.out.persistence.repository.UserMissionHistoryRepository
import com.teamfair.modulequest.adapter.out.persistence.repository.UserQuestHistoryRepository
import com.teamfair.modulequest.application.port.out.UserMissionHistoryPersistencePort
import com.teamfair.modulequest.domain.mapper.UserMissionHistoryMapper
import com.teamfair.modulequest.domain.model.UserMissionHistory
import org.springframework.stereotype.Component

@Component
class UserMissionHistoryPersistenceAdapter(
    private val userMissionHistoryRepository: UserMissionHistoryRepository,
    private val missionRepository: MissionRepository,
    private val userQuestHistoryRepository: UserQuestHistoryRepository
) : UserMissionHistoryPersistencePort {

    override fun save(userMissionHistory: UserMissionHistory): UserMissionHistory {
        val missionEntity = missionRepository.findById(userMissionHistory.missionId)
            .orElseThrow { IllegalArgumentException("Mission not found with id: ${userMissionHistory.missionId}") }
        
        val userQuestHistoryEntity = userQuestHistoryRepository.findById(userMissionHistory.userQuestHistoryId)
            .orElseThrow { IllegalArgumentException("UserQuestHistory not found with id: ${userMissionHistory.userQuestHistoryId}") }
        
        val entity = UserMissionHistoryMapper.toEntity(userMissionHistory, missionEntity, userQuestHistoryEntity)
        val savedEntity = userMissionHistoryRepository.save(entity)
        return UserMissionHistoryMapper.toModel(savedEntity)
    }

    override fun findById(id: Long): UserMissionHistory? {
        return userMissionHistoryRepository.findById(id)
            .map { UserMissionHistoryMapper.toModel(it) }
            .orElse(null)
    }

    override fun findAll(): List<UserMissionHistory> {
        return userMissionHistoryRepository.findAll()
            .map { UserMissionHistoryMapper.toModel(it) }
    }

    override fun findByUserId(userId: Long): List<UserMissionHistory> {
        return userMissionHistoryRepository.findByUserId(userId)
            .map { UserMissionHistoryMapper.toModel(it) }
    }

    override fun findByMissionId(missionId: Long): List<UserMissionHistory> {
        return userMissionHistoryRepository.findByMissionId(missionId)
            .map { UserMissionHistoryMapper.toModel(it) }
    }

    override fun deleteById(id: Long) {
        userMissionHistoryRepository.deleteById(id)
    }

    override fun existsById(id: Long): Boolean {
        return userMissionHistoryRepository.existsById(id)
    }
} 