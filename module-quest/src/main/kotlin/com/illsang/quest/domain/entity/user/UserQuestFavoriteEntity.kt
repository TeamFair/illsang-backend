package com.illsang.quest.domain.entity.user

import com.illsang.common.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "user_quest_favorite")
class UserQuestFavoriteEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "user_id", nullable = false)
    val userId: String,

    @Column(name = "quest_id", nullable = false)
    val questId: Long,
) : BaseEntity()
