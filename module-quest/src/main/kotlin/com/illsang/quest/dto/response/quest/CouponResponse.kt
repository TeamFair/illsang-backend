package com.illsang.quest.dto.response.quest

import com.illsang.quest.domain.model.quset.CouponModel
import java.time.LocalDateTime

data class CouponResponse(
    val id: Long,
    val name: String,
    val imageId: String?,
    val validFrom: LocalDateTime?,
    val validTo: LocalDateTime?,
    val storeId: Long?,
    val description: String?,
    val deleteYn: Boolean
) {
    companion object {
        fun from(model: CouponModel) = CouponResponse(
            id = model.id!!,
            name = model.name,
            imageId = model.imageId,
            validFrom = model.validFrom,
            validTo = model.validTo,
            storeId = model.storeId,
            description = model.description,
            deleteYn = model.deleteYn
        )
    }
}



