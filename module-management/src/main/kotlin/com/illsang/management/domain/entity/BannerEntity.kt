package com.illsang.management.domain.entity

import com.illsang.common.entity.BaseEntity
import com.illsang.management.dto.request.BannerUpdateRequest
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
    var bannerImageId: String? = null,

    @Column(columnDefinition = "TEXT")
    var description: String? = null,

    @Column(name = "use_yn", nullable = false)
    var useYn: Boolean = false,
) : BaseEntity() {

    init {
        validateTitle(this.title)
    }

    fun update(request: BannerUpdateRequest) {
        validateTitle(request.title)

        this.title = request.title
        this.description = request.description
        this.useYn = request.useYn
        this.bannerImageId = request.imageId
    }

    private fun validateTitle(title: String) {
        require(title.isNotBlank()) { "Title is required" }
        require(title.length <= 255) { "Title must be less than 255 characters" }
    }

}
