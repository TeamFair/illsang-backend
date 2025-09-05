package com.illsang.quest.domain.entity.quest

import com.illsang.common.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "store")
class StoreEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name="name")
    var name: String,

    @Column(name="address")
    var address: String? = null,

    @Column(name="phone_number")
    var phoneNumber: String? = null,

    @Column(name="description")
    var description: String? = null,

    @Column(name="manager_id")
    var managerId: String,

    @Column(name="active_yn")
    var activeYn: Boolean = true,

    @Column(name="image_id")
    var imageId: String? = null,

    @OneToMany(mappedBy = "store" , cascade = [CascadeType.ALL], orphanRemoval = true)
    val quests: MutableList<QuestEntity> = mutableListOf(),

    @OneToMany(mappedBy = "store", cascade = [CascadeType.ALL], orphanRemoval = true)
    val coupons: MutableList<CouponEntity> = mutableListOf(),

) : BaseEntity() {
    fun update(
        name: String? = null,
        address: String? = null,
        phoneNumber: String? = null,
        description: String? = null,
        activeYn: Boolean? = null,
        imageId: String? = null,
        managerId: String? = null,
    ) {
        name?.let { this.name = it }
        address?.let { this.address = it }
        phoneNumber?.let { this.phoneNumber = it }
        description?.let { this.description = it }
        activeYn?.let { this.activeYn = it }
        imageId?.let { this.imageId = it }
        managerId?.let { this.managerId = it }
    }

    fun addQuests(quests: List<QuestEntity>) {
        this.quests.addAll(quests)
    }

    fun addCoupons(coupons: List<CouponEntity>) {
        this.coupons.addAll(coupons)
    }
}