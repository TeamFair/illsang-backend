package com.illsang.management.domain.entity

import com.illsang.common.converter.StringListConverter
import com.illsang.common.entity.BaseEntity
import com.illsang.management.dto.request.MetroUpdateRequest
import jakarta.persistence.*

@Entity
@Table(name = "metro_area")
class MetroAreaEntity(
    @Id
    var code: String,

    @Column(name = "area_name")
    var areaName: String,

    @Column(name = "images")
    @Convert(converter = StringListConverter::class)
    var images: MutableList<String> = mutableListOf(),

    @OneToMany(mappedBy = "metroArea", cascade = [CascadeType.ALL], orphanRemoval = true)
    val commercialAreas: MutableList<CommercialAreaEntity> = mutableListOf(),
) : BaseEntity() {
    fun update(request: MetroUpdateRequest) {
        this.code = request.code
        this.areaName = request.areaName
        this.images = request.images?.toMutableList() ?: mutableListOf()
    }
}
