package com.illsang.user.domain.entity

import com.illsang.common.enums.PointType
import jakarta.persistence.*
import java.io.Serializable

@Embeddable
data class UserPointKey(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: UserEntity,

    @Column(name = "season_id", nullable = false)
    val seasonId: Long,

    @Column(name = "metro_area_code", nullable = false)
    val metroAreaCode: String,

    @Column(name = "commercial_area_code", nullable = false)
    val commercialAreaCode: String,

    @Column(name = "point_type", nullable = false)
    @Enumerated(EnumType.STRING)
    var pointType: PointType,
): Serializable
