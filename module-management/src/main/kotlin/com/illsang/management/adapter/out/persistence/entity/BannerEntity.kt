package com.illsang.management.adapter.out.persistence.entity

import com.illsang.common.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "banner")
class BannerEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, length = 255)
    var title: String,

    @Column(name = "banner_image_id")
    var bannerImageId: Long? = null,

    @Column(columnDefinition = "TEXT")
    var description: String? = null,

    @Column(name = "active_yn", nullable = false)
    var activeYn: Boolean = true
) : BaseEntity() 