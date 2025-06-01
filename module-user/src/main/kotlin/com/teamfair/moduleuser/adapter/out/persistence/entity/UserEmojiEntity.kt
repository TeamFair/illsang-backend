package com.teamfair.moduleuser.adapter.out.persistence.entity

import com.illsang.common.entity.BaseEntity
import com.teamfair.moduleuser.domain.model.TargetType
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "user_emoji")
class UserEmojiEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var userEntity: UserEntity,

    @Column(name = "target_id", nullable = false)
    val targetId: UUID,

    @Column(name = "target_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    val targetType: TargetType
) : BaseEntity()