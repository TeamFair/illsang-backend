package com.illsang.user.service

import com.illsang.common.enums.PointType
import com.illsang.common.event.management.area.CommercialAreaGetEvent
import com.illsang.common.event.management.area.MetroAreaGetEvent
import com.illsang.common.event.management.season.SeasonGetCurrentEvent
import com.illsang.common.event.user.point.UserPointCreateRequest
import com.illsang.user.domain.entity.UserPointHistoryEntity
import com.illsang.user.domain.entity.UserPointKey
import com.illsang.user.domain.model.UserRankModel
import com.illsang.user.dto.response.CommercialRankResponse
import com.illsang.user.dto.response.MetroRankResponse
import com.illsang.user.repository.UserPointHistoryRepository
import com.illsang.user.repository.UserPointRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.ZoneId


@Service
@Transactional(readOnly = true)
class UserPointService(
    private val userService: UserService,
    private val userPointRepository: UserPointRepository,
    private val userPointHistoryRepository: UserPointHistoryRepository,
    private val eventPublisher: ApplicationEventPublisher,
) {

    @Transactional
    fun createPoints(userId: String, questId: Long, request: List<UserPointCreateRequest>) {
        val user = this.userService.findById(userId)
        val currentSeasonEvent = SeasonGetCurrentEvent(LocalDateTime.now(ZoneId.of("Asia/Seoul")))
        this.eventPublisher.publishEvent(currentSeasonEvent)
        val currentSeason = currentSeasonEvent.response

        val userPoints = request.map { pointRequest ->
            Pair(
                UserPointKey(
                    user = user,
                    seasonId = currentSeason.seasonId,
                    metroAreaCode = pointRequest.metroAreaCode,
                    commercialAreaCode = pointRequest.commercialAreaCode,
                    pointType = pointRequest.pointType,
                ),
                if (user.commercialAreaCode == pointRequest.commercialAreaCode) pointRequest.point * 2 else pointRequest.point,
            )
        }

        user.addPoints(userPoints)
        this.userPointHistoryRepository.saveAll(
            userPoints.map {
                UserPointHistoryEntity(
                    userId = it.first.user.id!!,
                    seasonId = it.first.seasonId,
                    metroAreaCode = it.first.metroAreaCode,
                    commercialAreaCode = it.first.commercialAreaCode,
                    pointType = it.first.pointType,
                    point = it.second,
                    questId = questId,
                )
            }
        )
    }

    fun findAllUserTotalRank(commercialAreaCode: String, pageable: Pageable): Page<UserRankModel> {
        return this.userPointRepository.findAllUserRank(
            commercialAreaCode = commercialAreaCode,
            pageable = pageable,
        )
    }

    fun findAllRankByUserContribution(seasonId: Long?, pageable: Pageable): Page<UserRankModel> {
        return this.userPointRepository.findAllUserRank(
            seasonId = seasonId,
            pointType = PointType.CONTRIBUTION,
            pageable = pageable,
        )
    }

    fun findAllRankByUserMetro(seasonId: Long?, metroAreaCode: String, pageable: Pageable): Page<UserRankModel> {
        return this.userPointRepository.findAllUserRank(
            seasonId = seasonId,
            pointType = PointType.METRO,
            metroCode = metroAreaCode,
            pageable = pageable,
        )
    }

    fun findAllRankByUserCommercial(seasonId: Long?, commercialAreaCode: String, pageable: Pageable): Page<UserRankModel> {
        return this.userPointRepository.findAllUserRank(
            seasonId = seasonId,
            pointType = PointType.COMMERCIAL,
            metroCode = commercialAreaCode,
            pageable = pageable,
        )
    }

    fun findAllRankByMetroArea(seasonId: Long?, pageable: Pageable): Page<MetroRankResponse> {
        val metroRank = this.userPointRepository.findAllAreaRank(seasonId, PointType.METRO, pageable)

        val metroEvent = MetroAreaGetEvent(metroRank.content.mapNotNull { it?.first })
        this.eventPublisher.publishEvent(metroEvent)

        return metroRank.map {
            MetroRankResponse(
                metroCode = it?.first!!,
                point = it.second,
                areaName = metroEvent.response.find { metro -> metro.code == it.first }!!.areaName
            )
        }
    }

    fun findAllRankByCommercialArea(seasonId: Long?, pageable: Pageable): Page<CommercialRankResponse>? {
        val commercialRank = this.userPointRepository.findAllAreaRank(seasonId, PointType.COMMERCIAL, pageable)

        val commercialEvent = CommercialAreaGetEvent(commercialRank.content.mapNotNull { it?.first })
        this.eventPublisher.publishEvent(commercialEvent)

        return commercialRank.map {
            CommercialRankResponse(
                commercialCode = it?.first!!,
                point = it.second,
                areaName = commercialEvent.response.find { metro -> metro.code == it.first }!!.areaName
            )
        }
    }

    fun findRankByUser(seasonId: Long?, areaCode: String?, pointType: PointType, userId: String): UserRankModel {
        return this.userPointRepository.findUserRankPosition(
            userId = userId,
            seasonId = seasonId,
            pointType = pointType,
            areaCode = areaCode,
        )
    }

}
