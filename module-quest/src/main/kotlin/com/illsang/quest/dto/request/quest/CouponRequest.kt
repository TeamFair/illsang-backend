package com.illsang.quest.dto.request.quest

import com.illsang.common.util.PasswordUtil
import com.illsang.quest.domain.entity.quest.CouponEntity
import com.illsang.common.enums.CouponType
import com.illsang.quest.domain.entity.quest.StoreEntity
import jakarta.validation.constraints.NotBlank
import software.amazon.awssdk.annotations.NotNull
import java.time.LocalDateTime

data class CouponCreateRequest(
    val name: String,
    val imageId: String? = null,
    val password: String? = null,
    val validFrom: LocalDateTime? = null,
    val validTo: LocalDateTime? = null,
    val description: String? = null,
    val deleteYn: Boolean = false
) {
    fun toEntity(): CouponEntity = CouponEntity(
        name = name,
        imageId = imageId,
        password = password?.let { PasswordUtil.encode(it) },
        validFrom = validFrom,
        validTo = validTo,
        description = description,
        deleteYn = deleteYn,
    )
}

data class CouponUpdateRequest(
    val name: String? = null,
    val imageId: String? = null,
    val password: String? = null,
    val validFrom: LocalDateTime? = null,
    val validTo: LocalDateTime? = null,
    val description: String? = null,
    val deleteYn : Boolean = false
)




