package com.illsang.moduleuser.application.port.out

import com.illsang.moduleuser.domain.model.UserXpModel

interface UserXpPersistencePort {
    fun save(userXp: UserXpModel): UserXpModel
    fun findById(id: Long): UserXpModel?
    fun findByUserId(userId: Long): List<UserXpModel>
    fun findByUserIdAndXpType(userId: Long, xpType: com.illsang.moduleuser.domain.model.XpType): UserXpModel?
    fun deleteById(id: Long)
    fun existsById(id: Long): Boolean
}
