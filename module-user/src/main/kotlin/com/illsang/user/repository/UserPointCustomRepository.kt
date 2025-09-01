package com.illsang.user.repository

import com.illsang.common.enums.PointType
import com.illsang.user.domain.model.UserRankModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface UserPointCustomRepository {
    fun findAllUserRank(
        seasonId: Long? = null,
        metroCode: String? = null,
        commercialAreaCode: String? = null,
        pointType: PointType? = null,
        pageable: Pageable
    ): Page<UserRankModel>

    fun findUserRankPosition(
        userId: String,
        seasonId: Long?,
        areaCode: String?,
        pointType: PointType,
    ): Pair<UserRankModel, Long?>

    fun findAllAreaRank(seasonId: Long?, pointType: PointType, pageable: Pageable): Page<Pair<String, Long>>
    fun findOwnerPoint(pointType: PointType, userId: String): List<Pair<String, Long>>
    fun findOwnerPointStatistic(userId: String, seasonId: Long?): List<Pair<PointType, Long>>
}
