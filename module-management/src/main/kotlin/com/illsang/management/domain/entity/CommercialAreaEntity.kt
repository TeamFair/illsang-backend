package com.illsang.management.domain.entity

import com.illsang.common.converter.StringListConverter
import com.illsang.common.entity.BaseEntity
import com.illsang.management.dto.request.CommercialUpdateRequest
import jakarta.persistence.*

@Entity
@Table(name = "commercial_area")
class CommercialAreaEntity(
    @Id
    var code: String,

    @Column(name = "area_name")
    var areaName: String,

    @Column(name = "description")
    var description: String?,

    @Column(name = "images")
    @Convert(converter = StringListConverter::class)
    var images: MutableList<String> = mutableListOf(),

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "metro_area_code", nullable = false)
    var metroArea: MetroAreaEntity,
) : BaseEntity() {
    fun update(request: CommercialUpdateRequest) {
        this.code = request.code
        this.areaName = request.areaName
        this.description = request.description
        this.images = request.images?.toMutableList() ?: mutableListOf()
    }
}
