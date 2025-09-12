package com.illsang.quest.service.quest

import com.illsang.common.event.management.image.ImageExistOrThrowEvent
import com.illsang.common.event.management.store.StoreExistOrThrowEvent
import com.illsang.common.util.PasswordUtil
import com.illsang.quest.domain.entity.quest.CouponEntity
import com.illsang.quest.domain.model.quset.CouponModel
import com.illsang.quest.dto.request.quest.CouponCreateRequest
import com.illsang.quest.dto.request.quest.CouponUpdateRequest
import com.illsang.quest.repository.quest.CouponRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class CouponService(
    private val couponRepository: CouponRepository,
    private val eventPublisher: ApplicationEventPublisher,
) {

    @Transactional
    fun create(request: CouponCreateRequest): CouponModel {
        request.imageId?.let {
            this.eventPublisher.publishEvent(ImageExistOrThrowEvent(it))
        }
        request.storeId?.let {
            this.eventPublisher.publishEvent(StoreExistOrThrowEvent(it))
        }
        val saved = couponRepository.save(request.toEntity())
        return CouponModel.from(saved)
    }

    fun getById(id: Long): CouponModel {
        val entity = findById(id)
        return CouponModel.from(entity)
    }

    fun listByStore(storeId: Long?): List<CouponModel> {
        return couponRepository.findAllByStoreId(storeId).map(CouponModel::from)
    }

    @Transactional
    fun update(id: Long, request: CouponUpdateRequest): CouponModel {
        request.imageId?.let {
            this.eventPublisher.publishEvent(ImageExistOrThrowEvent(it))
        }
        request.storeId?.let {
            this.eventPublisher.publishEvent(StoreExistOrThrowEvent(it))
        }
        val entity = findById(id)

        entity.update(request)
        return CouponModel.from(entity)
    }

    @Transactional
    fun softDelete(id: Long) {
        val entity = this.findById(id)
        if (entity.deleteYn) return
        entity.deleteYn = true
    }

    fun findById(id: Long): CouponEntity {
        return couponRepository.findByIdOrNull(id) ?: throw IllegalArgumentException("존재하지 않는 쿠폰입니다. couponId=${id}")
    }


    fun validCouponById(id: Long) {
        val coupon = this.findById(id)
        val now: LocalDateTime = LocalDateTime.now()

        coupon.validFrom?.let {
            if (now.isBefore(it)) {
                throw IllegalArgumentException("유효기간 시작 전 쿠폰은 사용할 수 없습니다. couponId=${id}")
            }
        }
        coupon.validTo?.let {
            if (now.isAfter(it)) {
                throw IllegalArgumentException("이미 만료된 쿠폰은 사용할 수 없습니다. couponId=${id}")
            }
        }

        if (coupon.deleteYn) {
            throw IllegalArgumentException("삭제된 쿠폰입니다. couponId=${id}")
        }
    }


    fun verifyPassword(id: Long, rawPassword: String) {
        val entity = findById(id)
        val storedPassword = entity.password
        storedPassword?.let {
            if (!PasswordUtil.matches(rawPassword, it)) {
                throw IllegalArgumentException("비밀번호가 틀렸습니다.")
            }
        }
    }

    fun existCouponImageId(id: String) {
        val coupon = this.couponRepository.existsByImageId(id)
        if (coupon) {
            throw IllegalArgumentException("This image is already registered in a coupon.")
        }
    }

}