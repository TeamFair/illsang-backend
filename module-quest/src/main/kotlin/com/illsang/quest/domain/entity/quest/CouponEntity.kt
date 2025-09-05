package com.illsang.quest.domain.entity.quest

import com.illsang.common.entity.BaseEntity
import com.illsang.common.util.PasswordUtil
import com.illsang.quest.dto.request.quest.CouponUpdateRequest
import com.illsang.common.enums.CouponType
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "coupon")
class CouponEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "name")
    var name: String = "",

    @Column(name = "image_id")
    var imageId: String? = null,

    @Column(name = "password")
    var password: String? = null,

    @Column(name = "valid_from")
    var validFrom: LocalDateTime? = null,

    @Column(name = "valid_to")
    var validTo: LocalDateTime? = null,

    @Column(name = "description")
    var description: String? = null,

    @Column(name = "delete_yn")
    var deleteYn: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    var store: StoreEntity? = null,

    @OneToMany(mappedBy = "coupon", cascade = [CascadeType.ALL], orphanRemoval = false)
    val couponSettings: MutableList<CouponSettingEntity> = mutableListOf()

) : BaseEntity() {
    fun update(request: CouponUpdateRequest, store: StoreEntity) {
        request.name?.let { this.name = it }
        this.imageId = request.imageId
        this.password = request.password?.let { PasswordUtil.encode(it) } ?: this.password
        this.validFrom = request.validFrom
        this.validTo = request.validTo
        this.description = request.description
        this.deleteYn = request.deleteYn
        this.store = store

    }

    fun addCouponSetting(couponSettings: List<CouponSettingEntity>) {
        this.couponSettings.addAll(couponSettings)
    }

}
