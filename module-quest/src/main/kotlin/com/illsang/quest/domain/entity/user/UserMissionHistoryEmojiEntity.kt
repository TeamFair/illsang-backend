package com.illsang.quest.domain.entity.user

import com.illsang.common.entity.BaseEntity
import com.illsang.quest.enums.EmojiType
import jakarta.persistence.*

@Entity
@Table(name = "user_mission_history")
class UserMissionHistoryEmojiEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_mission_history_id")
    val missionHistory: UserMissionHistoryEntity,

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    val type : EmojiType,

    @Column(name = "user_id")
    val userId: String,
) : BaseEntity()
