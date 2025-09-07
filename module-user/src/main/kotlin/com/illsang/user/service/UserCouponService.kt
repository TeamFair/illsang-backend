package com.illsang.user.service

import com.illsang.common.enums.CouponType
import com.illsang.common.enums.RankType
import com.illsang.common.event.management.season.SeasonGetCurrentEvent
import com.illsang.common.event.management.store.StoreInfoGetEvent
import com.illsang.common.event.user.coupon.CouponExistOrThrowEvent
import com.illsang.common.event.user.coupon.CouponInfoGetEvent
import com.illsang.common.event.user.coupon.CouponPasswordVerificationOrThrowEvent
import com.illsang.common.event.user.coupon.CouponSettingInfoGetEvent
import com.illsang.common.event.user.quest.UserQuestHistoryRankingEvent
import com.illsang.user.domain.entity.UserCouponEntity
import com.illsang.user.domain.model.CouponModel
import com.illsang.user.domain.model.UserCouponModel
import com.illsang.user.domain.model.UserRankModel
import com.illsang.user.dto.request.UserCouponCreateRequest
import com.illsang.user.dto.request.UserCouponUpdateRequest
import com.illsang.user.repository.UserCouponRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.ZoneId

@Service
@Transactional(readOnly = true)
class UserCouponService(
    private val userCouponRepository: UserCouponRepository,
    private val eventPublisher: ApplicationEventPublisher,
    private val userService: UserService,
    private val userPointService: UserPointService,
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

    @Transactional
    fun issueForStore(setting: CouponSettingInfoGetEvent.CouponSettingInfo) {
        val coupon = setting.coupons ?: return
        val storeId = coupon.storeId ?: return

        val totalAmount = setting.amount ?: return
        val issuedCoupons = mutableListOf<UserCouponEntity>()

        val storeRankingEvent = UserQuestHistoryRankingEvent(setting.type, storeId, RankType.STORE)
        val commercialRankingEvent = UserQuestHistoryRankingEvent(setting.type, storeId, RankType.COMMERCIAL)
        val metroRankingEvent = UserQuestHistoryRankingEvent(setting.type, storeId, RankType.METRO)

        eventPublisher.publishEvent(storeRankingEvent)
        eventPublisher.publishEvent(commercialRankingEvent)
        eventPublisher.publishEvent(metroRankingEvent)

        val storeRanking = storeRankingEvent.response
        val commercialRanking = commercialRankingEvent.response
        val metroRanking = metroRankingEvent.response

        issueCouponsByRanking(issuedCoupons, totalAmount, coupon.id, storeRanking)
        issueCouponsByRanking(issuedCoupons, totalAmount, coupon.id, commercialRanking)
        issueCouponsByRanking(issuedCoupons, totalAmount, coupon.id, metroRanking, handleTies = true)
    }

    fun issueCouponToUser(userId: String, couponId: Long?) {
        val userCoupon = UserCouponEntity.issue(userId, couponId!!)
        userCouponRepository.save(userCoupon)
    }

    private fun issueCouponsByRanking(
        issuedCoupons: MutableList<UserCouponEntity>,
        totalAmount: Int,
        couponId: Long?,
        ranking: List<String>,
        handleTies: Boolean = false
    ) {
        val remaining = totalAmount - issuedCoupons.size
        if (remaining <= 0) return

        val alreadyIssued = issuedCoupons.map { it.userId }.toSet()

        // 중복 제거 후 남은 수만큼만 후보 선정
        val candidates = ranking.filter { it !in alreadyIssued }.take(remaining)

        val toIssue = if (handleTies && candidates.isNotEmpty()) {
            // handleTies=true이면 마지막 그룹 컷오프
            val lastUser = candidates.last()
            val lastIndex = ranking.indexOf(lastUser)
            val tiedUsers = ranking.drop(lastIndex) // 뒤쪽에 있는 동일 점수(동점자) 그룹
            candidates.dropLast(tiedUsers.size) // 마지막 그룹 제외
        } else {
            candidates
        }

        // 쿠폰 발급
        toIssue.forEach { userId -> issueCouponToUser(userId, couponId) }
        issuedCoupons.addAll(toIssue.map { UserCouponEntity.issue(it, couponId!!) })
    }
}