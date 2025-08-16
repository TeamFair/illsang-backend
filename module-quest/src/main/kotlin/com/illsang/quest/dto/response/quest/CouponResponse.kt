package com.illsang.quest.dto.response.quest

import com.illsang.quest.domain.model.quset.CouponModel
import com.illsang.quest.enums.CouponType
import java.time.LocalDateTime

data class CouponResponse(
    val id: Long,
    val type: CouponType,
    val name: String,
    val imageId: String?,
    val validFrom: LocalDateTime?,
    val validTo: LocalDateTime?,
    val storeId: Long?,
    val description: String?
) {
    companion object {
        fun from(model: CouponModel) = CouponResponse(
            id = model.id!!,
            type = model.type,
            name = model.name,
            imageId = model.imageId,
            validFrom = model.validFrom,
            validTo = model.validTo,
            storeId = model.storeId,
            description = model.description
        )
    }
}


data class CouponPasswordVerifyResponse(
    val success: Boolean
)
