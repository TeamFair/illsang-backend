package com.illsang.management.domain.entity

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "metro_area_code", nullable = false)
    var metroArea: MetroAreaEntity,
)
