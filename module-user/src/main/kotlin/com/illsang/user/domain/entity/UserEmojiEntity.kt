package com.illsang.user.domain.entity

import com.illsang.common.adapter.out.persistence.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "user_emoji")
class UserEmojiEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(name = "emoji_id", nullable = false)
    val emojiId: Long,

    @Column(name = "is_equipped", nullable = false)
    val isEquipped: Boolean = false,

    @Column(name = "target_id", nullable = false)
    val targetId: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    var userEntity: UserEntity? = null
) : BaseEntity()
