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

    @Column(name = "title")
    var title: String,

    @Column(name = "navigation_title")
    var navigationTitle: String,

    @Column(name = "banner_image_id")
    var bannerImageId: String? = null,

    @Column(name = "description")
    var description: String? = null,


    @Column(name = "use_yn")
    var useYn: Boolean = false,
) : BaseEntity() {

    init {
        validate()
    }

    fun update(request: BannerUpdateRequest) {
        this.title = request.title
        this.description = request.description
        this.useYn = request.useYn
        this.bannerImageId = request.imageId
        this.navigationTitle = request.navigationTitle

        validate()
    }

    private fun validate() {
        require(title.isNotBlank()) { "Title is required" }
        require(navigationTitle.isNotBlank()) { "NavigationTitle is required" }
    }

}
