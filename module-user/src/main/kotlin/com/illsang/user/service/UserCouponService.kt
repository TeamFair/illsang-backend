package com.illsang.user.service

import com.illsang.common.event.user.coupon.CouponExistOrThrowEvent
import com.illsang.common.event.user.coupon.CouponInfoGetEvent
import com.illsang.common.event.user.coupon.CouponPasswordVerificationOrThrowEvent
import com.illsang.user.domain.entity.UserCouponEntity
import com.illsang.user.domain.model.CouponModel
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
    private val eventPublisher: ApplicationEventPublisher,
    private val userService: UserService,
) {
    fun getById(id: Long): UserCouponModel {
        val entity = findById(id)
        return toModelWithCoupon(entity)
    }

    fun listByUser(userId: String, page: Int, size: Int): List<UserCouponModel> {
        val pageable = PageRequest.of(page, size)
        return userCouponRepository.findAllByUserId(userId, pageable)
            .content
            .map(::toModelWithCoupon)
    }

    @Transactional
    fun create(request: UserCouponCreateRequest): UserCouponModel {
        // 쿠폰 존재 검증
        eventPublisher.publishEvent(CouponExistOrThrowEvent(request.couponId))

        // 발급
        val entity = UserCouponEntity.issue(request.userId, request.couponId)
        val saved = userCouponRepository.save(entity)

        return toModelWithCoupon(saved)
    }

    @Transactional
    fun update(id: Long, request: UserCouponUpdateRequest): UserCouponModel {
        val entity = findById(id)

        // 쿠폰 존재 검증 (수정: couponId로 검증)
        eventPublisher.publishEvent(CouponExistOrThrowEvent(entity.couponId))

        entity.update(request)
        return toModelWithCoupon(entity)
    }

    fun verifyPassword(id: Long, rawPassword: String) {
        val entity = findById(id)
        eventPublisher.publishEvent(
            CouponPasswordVerificationOrThrowEvent(entity.couponId, rawPassword)
        )
    }

    private fun findById(id: Long): UserCouponEntity =
        userCouponRepository.findByIdOrNull(id)
            ?: throw IllegalArgumentException("UserCoupon not found with id: $id")

    /**
     * 공통 변환 로직: UserCouponEntity -> UserCouponModel(with coupon info)
     */
    private fun toModelWithCoupon(entity: UserCouponEntity): UserCouponModel {
        val coupon = getCouponInfo(entity.couponId)
        val userCoupon = UserCouponModel.from(entity, coupon)
        return userCoupon
    }

    private fun getCouponInfo(couponId: Long): CouponModel {
        val event = CouponInfoGetEvent(couponId)
        eventPublisher.publishEvent(event)
        val coupon = event.response

        val storeName = userService.getUser(event.response.storeId).nickname

        return CouponModel(
            id = coupon.id,
            couponType = coupon.type,
            name = coupon.name,
            imageId = coupon.imageId,
            storeName = storeName,
            description = coupon.description,
            validFrom = coupon.validFrom,
            validTo = coupon.validTo,
        )
    }
}
