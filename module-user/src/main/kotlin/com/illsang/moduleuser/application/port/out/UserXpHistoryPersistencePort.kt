package com.illsang.moduleuser.application.port.out

import com.illsang.moduleuser.domain.model.UserXpHistoryModel

interface UserXpHistoryPersistencePort {
    fun save(userXpHistory: UserXpHistoryModel): UserXpHistoryModel
    fun findById(id: Long): UserXpHistoryModel?
    fun findByUserId(userId: Long): List<UserXpHistoryModel>
    fun findByUserIdAndXpType(userId: Long, xpType: com.illsang.moduleuser.domain.model.XpType): List<UserXpHistoryModel>
    fun deleteById(id: Long)
    fun existsById(id: Long): Boolean
}
