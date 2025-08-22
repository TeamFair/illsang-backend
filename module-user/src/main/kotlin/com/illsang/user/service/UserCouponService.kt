package com.illsang.user.service

import com.illsang.common.event.user.coupon.CouponExistOrThrowEvent
import com.illsang.common.event.user.coupon.CouponPasswordVerificationOrThrowEvent
import com.illsang.user.domain.entity.UserCouponEntity
import com.illsang.user.domain.model.UserCouponModel
import com.illsang.user.dto.request.UserCouponCreateRequest
import com.illsang.user.dto.request.UserCouponUpdateRequest
import com.illsang.user.repository.UserCouponRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserCouponService(
    private val userCouponRepository: UserCouponRepository,
    private val eventPublisher: ApplicationEventPublisher
) {

    fun getById(id: Long): UserCouponModel {
        val entity = this.findById(id)
        return UserCouponModel.from(entity)
    }

    fun listByUser(userId: String, page: Int, size: Int): List<UserCouponModel> {
        val pageable = PageRequest.of(page, size)
        return userCouponRepository.findAllByUserId(userId, pageable)
            .content
            .map(UserCouponModel::from)
    }

    @Transactional
    fun create(request: UserCouponCreateRequest): UserCouponModel {
        // 쿠폰 검증(quest 모듈 리스너가 처리)
        eventPublisher.publishEvent(CouponExistOrThrowEvent(request.couponId))

        // 도메인 정책: 발급은 엔티티 팩토리로만 생성(항상 미사용/미만료)
        val entity = UserCouponEntity.issue(request.userId, request.couponId)

        val saved = userCouponRepository.save(entity)
        return UserCouponModel.from(saved)
    }

    @Transactional
    fun update(id: Long, request: UserCouponUpdateRequest): UserCouponModel {
        // 쿠폰 검증(quest 모듈 리스너가 처리)
        eventPublisher.publishEvent(CouponExistOrThrowEvent(id))
        val entity = findById(id)
        entity.update(request)
        return UserCouponModel.from(entity)
    }

    fun verifyPassword(id: Long, rawPassword: String) {
        val userCoupon = this.findById(id)
        eventPublisher.publishEvent(CouponPasswordVerificationOrThrowEvent(userCoupon.couponId, rawPassword))

    }

    private fun findById(id: Long): UserCouponEntity =
        userCouponRepository.findByIdOrNull(id) ?: throw IllegalArgumentException("UserCoupon not found with id: $id")
}
