package com.illsang.user.domain.entity

import com.illsang.common.entity.BaseEntity
import com.illsang.user.enums.PointType
import jakarta.persistence.*

@Entity
@Table(name = "user_point")
class UserPointEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: UserEntity,

    @Column(name = "point_type", nullable = false)
    @Enumerated(EnumType.STRING)
    var pointType: PointType,

    @Column(name = "point")
    var point: Int,
) : BaseEntity()
