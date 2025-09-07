package com.illsang.management.domain.entity

import com.illsang.common.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "store")
class StoreEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "name")
    var name: String,

    @Column(name = "address")
    var address: String? = null,

    @Column(name = "phone_number")
    var phoneNumber: String? = null,

    @Column(name = "description")
    var description: String? = null,

    @Column(name = "manager_id")
    var managerId: String,

    @Column(name = "active_yn")
    var activeYn: Boolean = true,

    @Column(name = "image_id")
    var imageId: String? = null,

    @Column(name = "commercial_area_code")
    var commercialAreaCode: String? = null,

    @Column(name = "metro_area_code")
    var metroAreaCode: String? = null,

    ) : BaseEntity() {
    fun update(
        name: String? = null,
        address: String? = null,
        phoneNumber: String? = null,
        description: String? = null,
        activeYn: Boolean? = null,
        imageId: String? = null,
        managerId: String? = null,
        commercialAreaCode: String? = null,
        metroAreaCode: String? = null,
    ) {
        name?.let { this.name = it }
        address?.let { this.address = it }
        phoneNumber?.let { this.phoneNumber = it }
        description?.let { this.description = it }
        activeYn?.let { this.activeYn = it }
        imageId?.let { this.imageId = it }
        managerId?.let { this.managerId = it }
        commercialAreaCode?.let { this.commercialAreaCode = it }
        metroAreaCode?.let { this.metroAreaCode = it }
    }

}