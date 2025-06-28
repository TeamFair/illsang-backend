package com.illsang.management.adapter.out.persistence.entity

import com.illsang.common.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "image")
class ImageEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, length = 50)
    var type: String,

    @Column(nullable = false, length = 255)
    var name: String,

    @Column(nullable = false)
    var size: Long
) : BaseEntity() 