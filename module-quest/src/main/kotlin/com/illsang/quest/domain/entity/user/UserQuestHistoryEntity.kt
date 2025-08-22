package com.illsang.quest.domain.entity.user

import com.illsang.common.entity.BaseEntity
import com.illsang.quest.domain.entity.quest.QuestEntity
import com.illsang.quest.enums.QuestHistoryStatus
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "user_quest_history")
class UserQuestHistoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "user_id")
    val userId: String,

    @Column(name = "season_id")
    val seasonId: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quest_id")
    val quest: QuestEntity,

    @OneToMany(mappedBy = "questHistory", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val missionHistory: MutableList<UserMissionHistoryEntity> = mutableListOf(),

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    var status: QuestHistoryStatus = QuestHistoryStatus.PROGRESSING,

    @Column(name = "completed_at")
    val completedAt: LocalDateTime? = null,
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
            status = QuestHistoryStatus.COMPLETE
        }
    }

}
