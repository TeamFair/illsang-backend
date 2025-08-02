package com.illsang.user.domain.entity

import com.illsang.common.enums.PointType
import jakarta.persistence.*
import java.io.Serializable

@Embeddable
data class UserPointKey(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: UserEntity,

    @Column(name = "season_id")
    val seasonId: Long,

    @Column(name = "area_code")
    val areaCode: String? = null,

    @Column(name = "point_type", nullable = false)
    @Enumerated(EnumType.STRING)
    var pointType: PointType,
): Serializable
