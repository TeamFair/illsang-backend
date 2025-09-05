package com.illsang.quest.service.quest

import com.illsang.quest.domain.entity.quest.CouponSettingEntity
import com.illsang.quest.domain.model.quset.CouponSettingModel
import com.illsang.quest.dto.request.quest.CouponSettingCreateRequest
import com.illsang.quest.dto.request.quest.CouponSettingUpdateRequest
import com.illsang.quest.repository.quest.CouponSettingRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CouponSettingService(
    private val couponService: CouponService,
    private val couponSettingRepository: CouponSettingRepository,
) {
    fun get(id: Long): CouponSettingModel {
        val couponSetting = this.findById(id)
        return CouponSettingModel.from(couponSetting)
    }

    fun getAll(): List<CouponSettingModel> {
        return couponSettingRepository.findAll().map(CouponSettingModel::from)
    }

    @Transactional
    fun deleteById(id: Long) {
        couponSettingRepository.deleteById(id)
    }

    @Transactional
    fun create(request: CouponSettingCreateRequest): CouponSettingModel{
        val coupon = this.couponService.findById(request.couponId)
        val couponSetting = request.toEntity(coupon)
        this.couponSettingRepository.save(couponSetting)

        return CouponSettingModel.from(couponSetting)
    }

    @Transactional
    fun update(id:Long, request: CouponSettingUpdateRequest): CouponSettingModel{
        val coupon = this.couponService.findById(request.couponId)
        val couponSetting = this.findById(id)

        couponSetting.update(request, coupon)
        return CouponSettingModel.from(couponSetting)
    }

    fun findById(id: Long): CouponSettingEntity {
        val couponSetting = couponSettingRepository.findByIdOrNull(id) ?:
        throw IllegalArgumentException("존재하지 않는 쿠폰 셋팅입니다. couponSettingId=${id}")

        return couponSetting
    }

}