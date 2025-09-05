package com.illsang.quest.domain.entity.quest

import com.illsang.common.entity.BaseEntity
import com.illsang.common.enums.CouponType
import com.illsang.quest.dto.request.quest.CouponSettingUpdateRequest
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "coupon_setting")
class CouponSettingEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    var coupon: CouponEntity?= null,

    @Column(name = "type")
    var type: CouponType,

    @Column(name = "amount")
    var amount: Int?,

) : BaseEntity(){

    fun update(request: CouponSettingUpdateRequest, coupon: CouponEntity?){
        this.coupon = coupon
        this.type = request.type
        this.amount = request.amount
    }
}