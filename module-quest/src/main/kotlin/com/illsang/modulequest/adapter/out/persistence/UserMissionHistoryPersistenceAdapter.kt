package com.illsang.modulequest.adapter.out.persistence

import com.illsang.modulequest.adapter.out.persistence.repository.MissionRepository
import com.illsang.modulequest.adapter.out.persistence.repository.UserMissionHistoryRepository
import com.illsang.modulequest.adapter.out.persistence.repository.UserQuestHistoryRepository
import com.illsang.modulequest.application.port.out.UserMissionHistoryPersistencePort
import com.illsang.modulequest.domain.mapper.UserMissionHistoryMapper
import com.illsang.modulequest.domain.model.UserMissionHistoryModel
import org.springframework.stereotype.Component

@Component
class UserMissionHistoryPersistenceAdapter(
    private val userMissionHistoryRepository: UserMissionHistoryRepository,
    private val missionRepository: MissionRepository,
    private val userQuestHistoryRepository: UserQuestHistoryRepository
) : UserMissionHistoryPersistencePort {

    override fun save(userMissionHistoryModel: UserMissionHistoryModel): UserMissionHistoryModel {
        val missionEntity = missionRepository.findById(userMissionHistoryModel.missionId)
            .orElseThrow { IllegalArgumentException("Mission not found with id: ${userMissionHistoryModel.missionId}") }

        val userQuestHistoryEntity = userQuestHistoryRepository.findById(userMissionHistoryModel.userQuestHistoryId)
            .orElseThrow { IllegalArgumentException("UserQuestHistory not found with id: ${userMissionHistoryModel.userQuestHistoryId}") }

        val entity = UserMissionHistoryMapper.toEntity(userMissionHistoryModel, missionEntity, userQuestHistoryEntity)
        val savedEntity = userMissionHistoryRepository.save(entity)
        return UserMissionHistoryMapper.toModel(savedEntity)
    }

    override fun findById(id: Long): UserMissionHistoryModel? {
        return userMissionHistoryRepository.findById(id)
            .map { UserMissionHistoryMapper.toModel(it) }
            .orElse(null)
    }

    override fun findAll(): List<UserMissionHistoryModel> {
        return userMissionHistoryRepository.findAll()
            .map { UserMissionHistoryMapper.toModel(it) }
    }

    override fun findByUserId(userId: Long): List<UserMissionHistoryModel> {
        return userMissionHistoryRepository.findByUserId(userId)
            .map { UserMissionHistoryMapper.toModel(it) }
    }

    override fun findByMissionId(missionId: Long): List<UserMissionHistoryModel> {
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
