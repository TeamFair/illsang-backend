package com.illsang.quest.domain.entity.history

import com.illsang.common.entity.BaseEntity
import com.illsang.quest.domain.entity.quest.QuestEntity
import com.illsang.quest.enums.QuestStatus
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "user_quest_history")
class UserQuestHistoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "user_id", nullable = false)
    val userId: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quest_id")
    val quest: QuestEntity,

    @OneToMany(mappedBy = "questHistory", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val missionHistory: MutableList<UserMissionHistoryEntity> = mutableListOf(),

    @Enumerated(EnumType.STRING)
    var status: QuestStatus = QuestStatus.PROGRESSING,

    @Column(name = "completed_at")
    val completedAt: LocalDateTime? = null,

    @Column(name = "like_count")
    val likeCount: Int = 0,

    @Column(name = "hate_count")
    val hateCount: Int = 0,

    @Column(name = "view_count")
    val viewCount: Int = 0,
) : BaseEntity() {

    fun addMissionHistory(missionHistory: UserMissionHistoryEntity) {
        this.missionHistory.add(missionHistory)
    }

    fun complete() {
        val missionHistoryMap = this.missionHistory.associateBy { it.mission.id }

        val result = this.quest.missions.all { mission ->
            missionHistoryMap.containsKey(mission.id)
        }

        if (result) {
            status = QuestStatus.COMPLETE
        }
    }

}
