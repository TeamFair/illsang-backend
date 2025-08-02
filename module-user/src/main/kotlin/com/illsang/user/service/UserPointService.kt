package com.illsang.user.service

import com.illsang.common.event.management.point.UserPointCreateRequest
import com.illsang.common.event.management.season.SeasonGetCurrentEvent
import com.illsang.user.domain.entity.UserPointHistoryEntity
import com.illsang.user.domain.entity.UserPointKey
import com.illsang.user.repository.UserPointHistoryRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.ZoneId


@Service
@Transactional(readOnly = true)
class UserPointService(
    private val userService: UserService,
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
                    areaCode = pointRequest.areaCode,
                    pointType = pointRequest.pointType,
                ),
                pointRequest.point,
            )
        }

        user.addPoints(userPoints)
        this.userPointHistoryRepository.saveAll(
            userPoints.map {
                UserPointHistoryEntity(
                    userId = it.first.user.id!!,
                    seasonId = it.first.seasonId,
                    areaCode = it.first.areaCode,
                    pointType = it.first.pointType,
                    point = it.second,
                    questId = questId,
                )
            }
        )
    }

}
