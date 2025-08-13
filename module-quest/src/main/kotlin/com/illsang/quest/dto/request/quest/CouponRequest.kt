package com.illsang.quest.dto.request.quest

import com.illsang.common.util.PasswordUtil
import com.illsang.quest.domain.entity.quest.CouponEntity
import com.illsang.quest.enums.CouponType
import java.time.LocalDateTime

data class CreateCouponRequest(
    val couponType: CouponType,
    val name: String,
    val imageId: Long? = null,
    val expiresAt: LocalDateTime? = null,
    val description: String? = null,
    val validFrom: LocalDateTime? = null,
    val validTo: LocalDateTime? = null,
    val storeId: Long? = null,
    val passWord: String? = null

){
    fun toEntity() : CouponEntity {
        return CouponEntity(
            type = this.couponType,
            name = this.name,
            imageId = this.imageId,
            passWord = this.passWord?.let { PasswordUtil.encode(it) },
            validFrom = this.validFrom,
            validTo = this.validTo,
            storeId = this.storeId,
            description = this.description
        );
    }
}


