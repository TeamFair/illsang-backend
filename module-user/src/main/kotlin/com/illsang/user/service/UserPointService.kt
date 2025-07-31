package com.illsang.user.service

import com.illsang.common.event.management.point.UserPointCreateRequest
import com.illsang.user.domain.entity.UserPointEntity
import com.illsang.user.enums.PointType
import com.illsang.user.repository.UserPointRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
class UserPointService(
    private val userService: UserService,
    private val userPointRepository: UserPointRepository,
) {

    @Transactional
    fun createPoints(userId: String, request: List<UserPointCreateRequest>) {
        val user = this.userService.findById(userId)
        val userPoints = request.map {
            UserPointEntity(
                user = user,
                pointType = PointType.valueOf(it.pointType),
                point = it.point,
            )
        }

        user.addPoints(userPoints)

        this.userPointRepository.saveAll(userPoints)
    }

}
