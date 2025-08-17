package com.illsang.management.domain.entity

import com.illsang.common.converter.StringListConverter
import jakarta.persistence.*

@Entity
@Table(name = "commercial_area")
class CommercialAreaEntity(
    @Id
    val code: String,

    @Column(name = "area_name")
    var areaName: String,

    @Column(name = "description")
    var description: String,

    @Column(name = "images")
    @Convert(converter = StringListConverter::class)
    var images: MutableList<String> = mutableListOf(),

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "metro_area_code", nullable = false)
    var metroArea: MetroAreaEntity,
)
