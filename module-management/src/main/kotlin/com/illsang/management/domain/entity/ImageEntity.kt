package com.illsang.management.domain.entity

import com.illsang.common.entity.BaseEntity
import com.illsang.management.enums.ImageType
import jakarta.persistence.*

@Entity
@Table(name = "image")
class ImageEntity(
    @Id
    val id: String,

    @Enumerated(EnumType.STRING)
    var type: ImageType,

    @Column(nullable = false, length = 255)
    var fileName: String,

    @Column(nullable = false)
    var fileSize: Long,
) : BaseEntity() {
    init {
        require(fileName.isNotBlank()) { "Name is required" }
        require(fileSize > 0) { "Size must be greater than 0" }
        require(fileName.length <= 255) { "Name must be less than 255 characters" }
    }
}
