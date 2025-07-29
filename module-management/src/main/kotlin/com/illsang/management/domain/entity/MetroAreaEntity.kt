package com.illsang.management.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "metro_area")
class MetroAreaEntity(
    @Id
    val code: String,

    @Column(nullable = false, unique = true)
    var areaName: String,

    @OneToMany(mappedBy = "metroArea", cascade = [CascadeType.ALL], orphanRemoval = true)
    val commercialAreas: MutableList<CommercialAreaEntity> = mutableListOf(),
)
