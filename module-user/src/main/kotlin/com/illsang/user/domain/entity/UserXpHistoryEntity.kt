package com.illsang.user.domain.entity

import com.illsang.common.adapter.out.persistence.entity.BaseEntity
import com.illsang.user.enums.XpType
import jakarta.persistence.*

@Entity
@Table(name = "user_xp_history")
class UserXpHistoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var userEntity: UserEntity,

    @Column(name = "xp_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    val xpType: XpType,

    @Column(nullable = false)
    val point: Int
) : BaseEntity()
