package com.illsang.quest.dto.response.quest

import com.illsang.quest.domain.model.quset.CouponModel
import java.time.LocalDateTime

data class CouponResponse(
    val id: Long?,
    val name: String,
    val expiresAt: LocalDateTime?,
    val description: String?,
    val createdBy: String?,
    val createdAt: LocalDateTime?,
    val updatedBy: String?,
    val updatedAt: LocalDateTime?,
){
    companion object {
        fun from(couponModel: CouponModel) : CouponResponse{
            return CouponResponse(
                createdBy = couponModel.createdBy,
                createdAt = couponModel.createdAt,
                updatedBy = couponModel.updatedBy,
                updatedAt = couponModel.updatedAt,
                id = couponModel.id,
                name = "",
                expiresAt = LocalDateTime.now(),
                description = "",
            )
        }
    }
}