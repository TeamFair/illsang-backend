package com.illsang.management.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

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
