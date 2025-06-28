package com.teamfair.modulequest.application.port.out

import com.teamfair.modulequest.domain.model.UserMissionHistory

interface UserMissionHistoryPersistencePort {
    fun save(userMissionHistory: UserMissionHistory): UserMissionHistory
    fun findById(id: Long): UserMissionHistory?
    fun findAll(): List<UserMissionHistory>
    fun findByUserId(userId: Long): List<UserMissionHistory>
    fun findByMissionId(missionId: Long): List<UserMissionHistory>
    fun deleteById(id: Long)
    fun existsById(id: Long): Boolean
} 