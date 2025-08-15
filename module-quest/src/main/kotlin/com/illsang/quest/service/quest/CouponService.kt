package com.illsang.quest.service.quest

import com.illsang.quest.domain.entity.quest.CouponEntity
import com.illsang.quest.domain.model.quset.CouponModel
import com.illsang.quest.dto.request.quest.CouponCreateRequest
import com.illsang.quest.dto.request.quest.CouponUpdateRequest
import com.illsang.quest.repository.quest.CouponRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CouponService(
    private val couponRepository: CouponRepository
) {

    @Transactional
    fun create(request: CouponCreateRequest): CouponModel {
        val saved = couponRepository.save(request.toEntity())
        return CouponModel.from(saved)
    }

    fun getById(id: Long): CouponModel {
        val entity = findById(id)
        return CouponModel.from(entity)
    }

    fun listByStore(storeId: Long): List<CouponModel> {
        return couponRepository.findAllByStoreId(storeId).map(CouponModel::from)
    }

    @Transactional
    fun update(id: Long, request: CouponUpdateRequest): CouponModel {
        val entity = findById(id)
        entity.update(request)
        return CouponModel.from(entity)
    }

    @Transactional
    fun delete(id: Long) {
        val entity = findById(id)
        couponRepository.delete(entity)
    }

    private fun findById(id: Long): CouponEntity =
        couponRepository.findByIdOrNull(id) ?: throw IllegalArgumentException("Coupon not found with id: $id")


}