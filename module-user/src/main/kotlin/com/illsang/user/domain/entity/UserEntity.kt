package com.illsang.user.domain.entity

import com.illsang.common.adapter.out.persistence.entity.BaseEntity
import com.illsang.auth.enums.OAuthProvider
import com.illsang.common.entity.BaseEntity
import com.illsang.user.enums.UserStatus
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String? = null,

    @Column(nullable = false, unique = true, length = 100)
    var email: String,

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    var channel: OAuthProvider,

    @Column(nullable = false, length = 25)
    var nickname: String,

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    var status: UserStatus,

    @Column(name = "status_updated_at")
    var statusUpdatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "profile_image_id")
    var profileImageId: String? = null,

    @Column(name = "title_id")
    var titleHistoryId: String? = null,

    @OneToMany(mappedBy = "userEntity", cascade = [CascadeType.ALL], orphanRemoval = true)
    val userXpEntities: MutableList<UserXpEntity> = mutableListOf(),

    @OneToMany(mappedBy = "userEntity", cascade = [CascadeType.ALL], orphanRemoval = true)
    val xpHistories: MutableList<UserXpHistoryEntity> = mutableListOf(),

    @OneToMany(mappedBy = "userEntity", cascade = [CascadeType.ALL], orphanRemoval = true)
    val emojis: MutableList<UserEmojiEntity> = mutableListOf()
) : BaseEntity() {

    init {
        validateNickName(nickname)
        require(email.isNotBlank()) { "Email is required" }
        require(email.length <= 100) { "Email must be less than 100 characters" }
        require(nickname.length <= 25) { "Nickname must be less than 25 characters" }
    }

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

    fun updateNickname(nickName: String) {
        validateNickName(nickname)
        this.nickname = nickName
    }

    private fun validateNickName(nickname: String) {
        require(nickname.isNotBlank()) { "Nickname is required" }
    }

    fun updateProfileImage(imageId: String?) {
        this.profileImageId = imageId
    }

    fun updateTitle(titleHistoryId: String?) {
        if (titleHistoryId.isNullOrBlank()) {
            this.titleHistoryId = null
        } else {
            this.titleHistoryId = titleHistoryId
        }
    }
}
