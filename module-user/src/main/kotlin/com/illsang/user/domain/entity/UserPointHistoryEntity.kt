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

    @Column(name = "user_id")
    var userId: String,

    @Column(name = "season_id")
    val seasonId: Long,

    @Column(name = "metro_area_code")
    val metroAreaCode: String,

    @Column(name = "commercial_area_code")
    val commercialAreaCode: String,

    @Column(name = "point_type")
    @Enumerated(EnumType.STRING)
    var pointType: PointType,

    @Column(name = "point")
    val point: Int = 0,

    @Column(name = "quest_id")
    val questId: Long,

    @Column(name= "user_commercial_area_code")
    val userCommercialAreaCode: String? = null,

    @Column(name= "user_quest_history_id")
    val userQuestHistoryId: Long? = null,

) : BaseEntity()
