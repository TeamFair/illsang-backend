package com.teamfair.modulequest.adapter.out.persistence.entity

import com.illsang.common.entity.BaseEntity
import com.teamfair.modulequest.domain.model.enums.QuestStatus
import jakarta.persistence.*

@Entity
@Table(name = "user_quest_history")
class UserQuestHistoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quest_id", nullable = false)
    var quest: QuestEntity,

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    var status: QuestStatus = QuestStatus.PROGRESSING,

    @Column
    var liked: Boolean = false,

    @Column
    var disliked: Boolean = false,

    @Column(name = "view_count")
    var viewCount: Int = 0,

    @OneToMany(mappedBy = "userQuestHistory", cascade = [CascadeType.ALL])
    val missionHistories: MutableList<UserMissionHistoryEntity> = mutableListOf()
) : BaseEntity() {

    fun addMissionHistory(missionHistory: UserMissionHistoryEntity) {
        missionHistories.add(missionHistory)
        missionHistory.userQuestHistory = this
    }

    fun incrementViewCount() {
        viewCount++
    }
} 