package com.illsang.modulequest.adapter.out.persistence.entity

import com.illsang.common.adapter.out.persistence.entity.BaseEntity
import com.illsang.modulequest.domain.model.enums.MissionStatus
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "user_mission_history")
class UserMissionHistoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id", nullable = false)
    var mission: MissionEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_quest_history_id", nullable = false)
    var userQuestHistory: UserQuestHistoryEntity,

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    var status: MissionStatus = MissionStatus.PENDING,

    @Column(name = "submission_image_url", length = 512)
    var submissionImageUrl: String? = null,

    @Column(name = "submitted_at")
    var submittedAt: LocalDateTime? = null,

    @OneToMany(mappedBy = "userMissionHistory", cascade = [CascadeType.ALL])
    val quizHistories: MutableList<UserQuizHistoryEntity> = mutableListOf()
) : BaseEntity() {

    fun addQuizHistory(quizHistory: UserQuizHistoryEntity) {
        quizHistories.add(quizHistory)
        quizHistory.userMissionHistory = this
    }
}
