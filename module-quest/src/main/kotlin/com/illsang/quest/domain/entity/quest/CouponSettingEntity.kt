package com.illsang.quest.domain.entity.quest

import com.illsang.common.entity.BaseEntity
import com.illsang.common.enums.CouponType
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "coupon_setting")
class CouponSettingEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    var coupon: CouponEntity,

    @Column(name = "type")
    var type: CouponType,

    @Column(name = "amount")
    var amount: Int?,

) : BaseEntity(){

}