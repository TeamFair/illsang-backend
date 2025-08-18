package com.illsang.user.dto.request

import com.illsang.user.domain.entity.UserCouponEntity
import software.amazon.awssdk.annotations.NotNull

data class UserCouponCreateRequest(
    @field:NotNull
    val userId: Long,
    @field:NotNull
    val couponId: Long,
    val couponUseYn: Boolean = false,
    val couponExpireYn: Boolean = false
)

data class UserCouponUpdateRequest(
    val couponUseYn: Boolean? = null,
    val couponExpireYn: Boolean? = null
)

data class CouponPasswordVerifyRequest(
    val password: String
)