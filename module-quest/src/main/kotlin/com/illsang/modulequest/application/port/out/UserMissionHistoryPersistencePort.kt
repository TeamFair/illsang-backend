package com.illsang.modulequest.application.port.out

import com.illsang.modulequest.domain.model.UserMissionHistoryModel

interface UserMissionHistoryPersistencePort {
    fun save(userMissionHistoryModel: UserMissionHistoryModel): UserMissionHistoryModel
    fun findById(id: Long): UserMissionHistoryModel?
    fun findAll(): List<UserMissionHistoryModel>
    fun findByUserId(userId: Long): List<UserMissionHistoryModel>
    fun findByMissionId(missionId: Long): List<UserMissionHistoryModel>
    fun deleteById(id: Long)
    fun existsById(id: Long): Boolean
}
