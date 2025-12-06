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
    var coupon: CouponEntity? = null,

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    var type: CouponType,

    @Column(name = "amount")
    var amount: Int,

    @Column(name = "issued_amount")
    var issuedAmount: Int = 0,

    ) : BaseEntity() {

    fun update(request: CouponSettingUpdateRequest, coupon: CouponEntity?) {
        this.coupon = coupon
        this.type = request.type
        this.amount = request.amount
    }

    fun increaseIssuedAmount() {
        if (this.issuedAmount >= this.amount) {
            throw IllegalArgumentException("쿠폰이 모두 소진되었습니다.")
        }
        this.issuedAmount += 1
    }
}