package com.illsang.user.service

import com.illsang.common.enums.CouponType
import com.illsang.common.event.management.store.StoreInfoGetEvent
import com.illsang.common.event.user.coupon.CouponExistOrThrowEvent
import com.illsang.common.event.user.coupon.CouponInfoGetEvent
import com.illsang.common.event.user.coupon.CouponPasswordVerificationOrThrowEvent
import com.illsang.common.event.user.coupon.CouponSettingInfoGetEvent
import com.illsang.user.domain.entity.UserCouponEntity
import com.illsang.user.domain.model.CouponModel
import com.illsang.user.domain.model.UserCouponModel
import com.illsang.user.dto.request.UserCouponCreateRequest
import com.illsang.user.dto.request.UserCouponUpdateRequest
import com.illsang.user.repository.UserCouponRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
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

    fun listByUser(userId: String, pageable: Pageable): Page<UserCouponModel> {
        return userCouponRepository.findAllByUserId(userId, pageable)
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

        val storeEvent = StoreInfoGetEvent(coupon.storeId)
        eventPublisher.publishEvent(storeEvent)
        val store = storeEvent.response


        return CouponModel(
            id = coupon.id,
            name = coupon.name,
            imageId = coupon.imageId,
            storeName = store.storeName,
            description = coupon.description,
            validFrom = coupon.validFrom,
            validTo = coupon.validTo,
        )
    }

    fun issueCoupons(type: CouponType) {
        val settingEvent = CouponSettingInfoGetEvent()
        eventPublisher.publishEvent(settingEvent)

        settingEvent.response
            .filter { it.type == type }
            .forEach { issueForStore(it) }
    }

    private fun issueForStore(setting: CouponSettingInfoGetEvent.CouponSettingInfo) {
        val coupon = setting.coupons ?: return
        val storeId = coupon.storeId ?: return

        // 1. 스토어 조회
        val storeEvent = StoreInfoGetEvent(storeId)
        eventPublisher.publishEvent(storeEvent)
        val store = storeEvent.response

        val totalAmount = setting.amount ?: return
        val issuedCoupons = mutableListOf<UserCouponEntity>()

//        // 2. 발급 우선순위별 처리
//        issueByRanking(
//            issuedCoupons,
//            totalAmount
//        ) { remaining -> questRepository.findUserRankingByStore(store.id, remaining) }
//
//        issueByRanking(
//            issuedCoupons,
//            totalAmount
//        ) { remaining -> .findUserRankingByRegion(store.commercialAreaCode, remaining) }
//
//        issueByRanking(
//            issuedCoupons,
//            totalAmount,
//            handleTies = true
//        ) { remaining -> questRepository.findUserRankingByUpperRegion(store.commercialAreaCode, remaining) }

        // 3. 결과 로깅
//        logIssueResult(store, issuedCoupons)
    }


    /**
     * 공통 발급 로직
     */
//    private fun issueByRanking(
//        issuedCoupons: MutableList<UserCouponEntity>,
//        totalAmount: Int,
//        handleTies: Boolean = false,
//        rankingProvider: (remaining: Int) -> List<UserRanking>
//    ) {
//        val remaining = totalAmount - issuedCoupons.size
//        if (remaining <= 0) return
//
//        val ranking = rankingProvider(remaining)
//            .filter { user -> issuedCoupons.none { it.userId == user.id } }
//            .take(remaining)
//
//        val toIssue = if (handleTies) {
//            val lastScore = ranking.lastOrNull()?.score
//            val tiedUsers = ranking.filter { it.score == lastScore }
//            if (tiedUsers.size > 1) ranking - tiedUsers.toSet() else ranking
//        } else {
//            ranking
//        }
//
//        issuedCoupons.addAll(toIssue.map { user -> issueCouponToUser(ranking.first().coupon, user.id) })
//    }
}
