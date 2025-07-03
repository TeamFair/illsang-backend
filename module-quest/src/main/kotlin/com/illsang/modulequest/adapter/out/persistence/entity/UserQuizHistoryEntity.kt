package com.illsang.modulequest.adapter.out.persistence.entity

import com.illsang.common.adapter.out.persistence.entity.BaseEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "user_quiz_history")
class UserQuizHistoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    var quiz: QuizEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_mission_history_id", nullable = false)
    var userMissionHistory: UserMissionHistoryEntity,

    @Column(columnDefinition = "TEXT")
    var answer: String? = null,

    @Column(name = "submitted_at")
    var submittedAt: LocalDateTime? = null
) : BaseEntity()
