package com.illsang.management.domain.entity

import com.illsang.common.converter.StringListConverter
import jakarta.persistence.*

@Entity
@Table(name = "commercial_area")
class CommercialAreaEntity(
    @Id
    val code: String,

    @Column(nullable = false, unique = true)
    var areaName: String,

    @Column(nullable = false)
    var description: String,

    @Column(name = "image")
    @Convert(converter = StringListConverter::class)
    var images: MutableList<String> = mutableListOf(),

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "metro_area_code", nullable = false)
    var metroArea: MetroAreaEntity,
)
