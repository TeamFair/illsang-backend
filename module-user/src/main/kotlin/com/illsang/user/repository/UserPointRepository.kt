package com.illsang.user.repository

import com.illsang.common.enums.PointType
import com.illsang.user.domain.entity.UserPointEntity
import com.illsang.user.domain.entity.UserPointKey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserPointRepository : JpaRepository<UserPointEntity, UserPointKey>, UserPointCustomRepository {
    @Query("select sum(t.point) from UserPointEntity t where t.id.pointType = :pointType AND t.id.commercialAreaCode = :commercialAreaCode")
    fun sumPointByCommercialArea(pointType: PointType, commercialAreaCode: String): Long?
    fun findById_User_IdAndId_SeasonId(userId: String, seasonId: Long): List<UserPointEntity>
    @Query("select sum(t.point) from UserPointEntity t where t.id.user.id = :userId")
    fun sumPointByUser(userId: String): Long?
}
