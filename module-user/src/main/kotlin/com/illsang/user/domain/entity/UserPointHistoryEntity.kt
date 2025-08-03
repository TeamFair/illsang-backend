package com.illsang.user.domain.entity

import com.illsang.common.entity.BaseEntity
import com.illsang.common.enums.PointType
import jakarta.persistence.*

@Entity
@Table(name = "user_point_history")
class UserPointHistoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "user_id", nullable = false)
    var userId: String,

    @Column(name = "season_id")
    val seasonId: Long,

    @Column(name = "metro_area_code", nullable = false)
    val metroAreaCode: String,

    @Column(name = "commercial_area_code", nullable = false)
    val commercialAreaCode: String,

    @Column(name = "point_type", nullable = false)
    @Enumerated(EnumType.STRING)
    var pointType: PointType,

    @Column(name = "point")
    val point: Int = 0,

    @Column(name = "quest_id")
    val questId: Long,
) : BaseEntity()
