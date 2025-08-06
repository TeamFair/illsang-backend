package com.illsang.user.repository

import com.illsang.common.enums.PointType
import com.illsang.user.domain.entity.UserEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface UserPointCustomRepository {
    fun findAllUserRank(
        seasonId: Long? = null,
        commercialAreaCode: String? = null,
        pointType: PointType? = null,
        pageable: Pageable
    ): Page<Pair<UserEntity, Long>>
    fun findAllAreaRank(seasonId: Long?, pointType: PointType, pageable: Pageable): Page<Pair<String, Long>?>
}
