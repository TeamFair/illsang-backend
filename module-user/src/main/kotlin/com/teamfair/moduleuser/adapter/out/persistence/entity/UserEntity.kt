package com.teamfair.moduleuser.adapter.out.persistence.entity

import com.illsang.common.entity.BaseEntity
import com.teamfair.moduleuser.domain.model.UserStatus
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true, length = 100)
    var email: String,

    @Column(nullable = false, length = 50)
    var channel: String,

    @Column(nullable = false, length = 25)
    var nickname: String,

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    var status: UserStatus,

    @Column(name = "status_updated_at")
    var statusUpdatedAt: LocalDateTime? = null,

    @Column(name = "profile_image_id")
    var profileImageId: UUID? = null,

    @OneToMany(mappedBy = "userEntity", cascade = [CascadeType.ALL], orphanRemoval = true)
    val userXpEntities: MutableList<UserXpEntity> = mutableListOf(),

    @OneToMany(mappedBy = "userEntity", cascade = [CascadeType.ALL], orphanRemoval = true)
    val xpHistories: MutableList<UserXpHistoryEntity> = mutableListOf(),

    @OneToMany(mappedBy = "userEntity", cascade = [CascadeType.ALL], orphanRemoval = true)
    val emojis: MutableList<UserEmojiEntity> = mutableListOf()
) : BaseEntity() {

    fun addXp(xp: UserXpEntity) {
        userXpEntities.add(xp)
        xp.userEntity = this
    }

    fun addXpHistory(history: UserXpHistoryEntity) {
        xpHistories.add(history)
        history.userEntity = this
    }

    fun addEmoji(emoji: UserEmojiEntity) {
        emojis.add(emoji)
        emoji.userEntity = this
    }
}