package com.illsang.user.service

import com.illsang.common.enums.PointType
import com.illsang.common.event.management.quest.CompletedQuestHistoryCountGetEvent
import com.illsang.common.event.user.point.UserPointCreateRequest
import com.illsang.user.domain.entity.UserPointHistoryEntity
import com.illsang.user.domain.entity.UserPointKey
import com.illsang.user.domain.model.UserRankModel
import com.illsang.user.dto.response.*
import com.illsang.user.repository.UserPointHistoryRepository
import com.illsang.user.repository.UserPointRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
class UserPointService(
    private val userService: UserService,
    private val userPointRepository: UserPointRepository,
    private val userPointHistoryRepository: UserPointHistoryRepository,
    private val eventPublisher: ApplicationEventPublisher,
) {

    @Transactional
    fun createPoints(seasonId: Long, userId: String, questId: Long, request: List<UserPointCreateRequest>) {
        val user = this.userService.findById(userId)

        val userPoints = request.map { pointRequest ->
            Pair(
                UserPointKey(
                    user = user,
                    seasonId = seasonId,
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

    fun findAllUserTotalRank(commercialAreaCode: String): List<UserRankModel> {
        return this.userPointRepository.findAllUserRank(
            commercialAreaCode = commercialAreaCode,
            pageable = Pageable.ofSize(30),
        ).content
    }

    fun findAllRankByUserContribution(seasonId: Long?): List<UserRankModel> {
        return this.userPointRepository.findAllUserRank(
            seasonId = seasonId,
            pointType = PointType.CONTRIBUTION,
            pageable = Pageable.ofSize(30),
        ).content
    }

    fun findAllRankByUserMetro(seasonId: Long?, metroAreaCode: String): List<UserRankModel> {
        return this.userPointRepository.findAllUserRank(
            seasonId = seasonId,
            pointType = PointType.METRO,
            metroCode = metroAreaCode,
            pageable = Pageable.ofSize(30),
        ).content
    }

    fun findAllRankByUserCommercial(seasonId: Long?, commercialAreaCode: String): List<UserRankModel> {
        return this.userPointRepository.findAllUserRank(
            seasonId = seasonId,
            pointType = PointType.COMMERCIAL,
            commercialAreaCode = commercialAreaCode,
            pageable = Pageable.ofSize(30),
        ).content
    }

    fun findRankByUser(seasonId: Long?, areaCode: String?, pointType: PointType, userId: String): UserRankModel {
        return this.userPointRepository.findUserRankPosition(
            userId = userId,
            seasonId = seasonId,
            pointType = pointType,
            areaCode = areaCode,
        )
    }

    fun findPointByCommercial(userId: String): UserCommercialPointResponse {
        val ownerPoints = this.userPointRepository.findOwnerPoint(PointType.COMMERCIAL, userId)
        var topCommercialContributionResponse: UserTopCommercialPointResponse? = null
        if (ownerPoints.isNotEmpty()) {
            val commercialTotalPoint =
                this.userPointRepository.sumPointByCommercialArea(PointType.COMMERCIAL, ownerPoints[0].first)

            val contributionPercent = if (commercialTotalPoint > 0L) {
                (ownerPoints[0].second.toDouble() / commercialTotalPoint) * 100.0
            } else {
                0.0
            }

            topCommercialContributionResponse = UserTopCommercialPointResponse.from(
                code = ownerPoints[0].first,
                point = ownerPoints[0].second,
                contributionPercent = contributionPercent,
            )
        }

        return UserCommercialPointResponse.from(
            topCommercialArea = topCommercialContributionResponse,
            totalOwnerContributionList = ownerPoints.map {
                UserCommercialContributionResponse.from(
                    code = it.first,
                    point = it.second,
                )
            },
        )
    }

    fun findPointStatistic(userId: String, seasonId: Long?): UserPointStatisticResponse {
        val completedQuestEvent = CompletedQuestHistoryCountGetEvent(seasonId = seasonId, userId = userId)
        this.eventPublisher.publishEvent(completedQuestEvent)
        val completedQuestCount = completedQuestEvent.response

        val ownerPointStatistic = this.userPointRepository.findOwnerPointStatistic(userId, seasonId)

        return UserPointStatisticResponse(
            completedQuestCount = completedQuestCount,
            metroAreaPoint = ownerPointStatistic.findLast { it.first == PointType.METRO }?.second ?: 0,
            commercialAreaPoint = ownerPointStatistic.findLast { it.first == PointType.COMMERCIAL }?.second ?: 0,
            contributionPoint = ownerPointStatistic.findLast { it.first == PointType.CONTRIBUTION }?.second ?: 0,
        )
    }

    fun findPointSeasonSummary(userId: String, seasonId: Long): UserPointSummaryResponse {
        val userPoints = this.userPointRepository.findById_User_IdAndId_SeasonId(userId, seasonId)

        val topMetroAreaCode = userPoints
            .filter { it.id.pointType == PointType.METRO }
            .groupingBy { it.id.metroAreaCode }
            .fold(0L) { total, pointEntity -> total + pointEntity.point }
            .maxByOrNull { it.value }
            ?.key
        val topCommercialAreaCode = userPoints
            .filter { it.id.pointType == PointType.COMMERCIAL }
            .groupingBy { it.id.commercialAreaCode }
            .fold(0L) { total, pointEntity -> total + pointEntity.point }
            .maxByOrNull { it.value }
            ?.key
        val topContributionPoint = userPoints
            .filter { it.id.pointType == PointType.CONTRIBUTION }
            .groupingBy { it.id.commercialAreaCode }
            .fold(0L) { total, pointEntity -> total + pointEntity.point }
            .maxByOrNull { it.value }
            ?.value

        return UserPointSummaryResponse.from(
            topMetroAreaCode = topMetroAreaCode,
            topCommercialAreaCode = topCommercialAreaCode,
            topContributionPoint,
        )
    }

}
