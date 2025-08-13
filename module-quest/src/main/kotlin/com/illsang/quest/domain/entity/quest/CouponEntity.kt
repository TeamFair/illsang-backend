package com.illsang.quest.domain.entity.quest

import com.illsang.common.entity.BaseEntity
import com.illsang.quest.enums.CouponType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "coupon")
class CouponEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    var id: Long? = null,

    @Column(name = "coupon_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    var type: CouponType,

    @Column(name = "name", nullable = false, length = 100)
    var name: String = "",

    @Column(name = "image_id")
    var imageId: Long? = null,

    @Column(name = "password", length = 255)
    var passWord: String? = null,

    @Column(name = "valid_from")
    var validFrom: LocalDateTime? = null,

    @Column(name = "valid_to")
    var validTo: LocalDateTime? = null,

    @Column(name = "store_id")
    var storeId: Long? = null,

    @Column(name = "description", length = 100)
    var description: String? = null,

    ) : BaseEntity() {
}
