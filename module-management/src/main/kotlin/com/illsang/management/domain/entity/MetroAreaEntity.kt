package com.illsang.management.domain.entity

import com.illsang.common.converter.StringListConverter
import jakarta.persistence.*

@Entity
@Table(name = "metro_area")
class MetroAreaEntity(
    @Id
    val code: String,

    @Column(name = "area_name")
    var areaName: String,

    @Column(name = "image")
    @Convert(converter = StringListConverter::class)
    var images: MutableList<String> = mutableListOf(),

    @OneToMany(mappedBy = "metroArea", cascade = [CascadeType.ALL], orphanRemoval = true)
    val commercialAreas: MutableList<CommercialAreaEntity> = mutableListOf(),
)
