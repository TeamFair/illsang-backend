package com.illsang.quest.domain.entity.user

import com.illsang.common.entity.BaseEntity
import com.illsang.quest.domain.entity.quest.MissionEntity
import com.illsang.quest.enums.EmojiType
import com.illsang.quest.enums.MissionHistoryStatus
import com.illsang.quest.enums.QuestHistoryStatus
import jakarta.persistence.*

@Entity
@Table(name = "user_mission_history")
class UserMissionHistoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "user_id")
    val userId: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    val mission: MissionEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_quest_history_id")
    val questHistory: UserQuestHistoryEntity,

    @Column(name = "submit_image_id")
    val submitImageId: String? = null,

    @Column(name = "like_count")
    var likeCount: Int = 0,

    @Column(name = "hate_count")
    var hateCount: Int = 0,

    @Column(name = "view_count")
    var viewCount: Int = 0,

    @Column(name = "share_count")
    var shareCount: Int = 0,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    var status: MissionHistoryStatus = MissionHistoryStatus.SUBMITTED,

    @OneToOne(mappedBy = "missionHistory", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var quizHistory: UserQuizHistoryEntity? = null,

    @OneToMany(mappedBy = "missionHistory", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val missionHistoryEmojis: MutableList<UserMissionHistoryEmojiEntity> = mutableListOf(),

    @OneToMany(mappedBy = "missionHistory", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val missionHistoryComments: MutableList<UserMissionHistoryCommentEntity> = mutableListOf(),

) : BaseEntity() {

    fun addQuizHistory(quizHistory: UserQuizHistoryEntity) {
        this.quizHistory = quizHistory
    }

    fun increaseViewCount() {
        this.viewCount++
    }

    fun increaseShareCount() {
        this.shareCount++
    }

    fun addEmoji(emojiEntity: UserMissionHistoryEmojiEntity) {
        this.missionHistoryEmojis.add(emojiEntity)

        if (emojiEntity.type == EmojiType.LIKE) this.likeCount++
        else this.hateCount++
    }

    fun removeEmoji(emojiEntity: UserMissionHistoryEmojiEntity) {
        if (emojiEntity.type == EmojiType.LIKE) this.likeCount--
        else this.hateCount--
    }

    fun report() {
        this.status = MissionHistoryStatus.REPORTED
    }

    fun reject() {
        this.status = MissionHistoryStatus.REJECTED
    }

    fun approve() {
        this.status = MissionHistoryStatus.APPROVED
    }

}
