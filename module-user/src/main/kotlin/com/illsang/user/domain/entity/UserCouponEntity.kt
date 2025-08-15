package com.illsang.user.domain.entity

import com.illsang.common.entity.BaseEntity
import com.illsang.user.dto.request.UserCouponUpdateRequest
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "user_coupon")
class UserCouponEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    var id: Long? = null,

    @Column(name = "user_id", nullable = false)
    var userId: Long = 0,

    @Column(name = "coupon_id", nullable = false)
    var couponId: Long = 0,

    @Column(name = "use_yn", nullable = false)
    var couponUseYn: Boolean = false,

    @Column(name = "expire_yn", nullable = false)
    var couponExpireYn: Boolean = false,

    @Column(name = "used_at")
    var usedAt: LocalDateTime? = null


) : BaseEntity() {
    fun update(request: UserCouponUpdateRequest) {
        request.couponUseYn?.let {
            this.couponUseYn = it
            this.usedAt = if (it) LocalDateTime.now() else null
        }
        request.couponExpireYn?.let { this.couponExpireYn = it }
        this.updatedAt = LocalDateTime.now()
    }

}