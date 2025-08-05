package com.illsang.user.service

import com.illsang.common.event.management.season.SeasonGetCurrentEvent
import com.illsang.common.event.user.point.UserPointCreateRequest
import com.illsang.user.domain.entity.UserPointHistoryEntity
import com.illsang.user.domain.entity.UserPointKey
import com.illsang.user.dto.response.UserRankTotalResponse
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

    fun findAllTotalRank(commercialAreaCode: String, pageable: Pageable): Page<UserRankTotalResponse> {
        val userTotalRank = this.userPointRepository.findAllTotalRank(commercialAreaCode, pageable)

        return userTotalRank.map { UserRankTotalResponse.from(it) }
    }

}
