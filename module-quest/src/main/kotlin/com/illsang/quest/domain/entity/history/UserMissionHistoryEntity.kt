package com.illsang.quest.domain.entity.history

import com.illsang.common.entity.BaseEntity
import com.illsang.quest.domain.entity.quest.MissionEntity
import jakarta.persistence.*

@Entity
@Table(name = "user_mission_history")
class UserMissionHistoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "user_id", nullable = false)
    val userId: String,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mission_id")
    val mission: MissionEntity,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_quest_history_id")
    val questHistory: UserQuestHistoryEntity,

    @Column(name = "submit_image_id")
    val submitImageId: String? = null,

    @OneToOne(mappedBy = "missionHistory", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var quizHistory: UserQuizHistoryEntity? = null,
) : BaseEntity() {

    fun addQuizHistory(quizHistory: UserQuizHistoryEntity) {
        this.quizHistory = quizHistory
    }

}
