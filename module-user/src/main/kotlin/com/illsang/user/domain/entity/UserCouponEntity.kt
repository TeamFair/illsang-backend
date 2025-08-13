package com.illsang.user.domain.entity

import com.illsang.common.entity.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

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

    @Column(name = "coupon_use_yn", nullable = false)
    var couponUseYn: Boolean = false,

    @Column(name = "coupon_expire_yn", nullable = false)
    var couponExpireYn: Boolean = false,


    ) : BaseEntity(){

    }