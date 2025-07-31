package com.illsang.quest.domain.entity.history

import com.illsang.common.entity.BaseEntity
import com.illsang.quest.domain.entity.quest.QuizAnswerEntity
import com.illsang.quest.domain.entity.quest.QuizEntity
import jakarta.persistence.*

@Entity
@Table(name = "user_quiz_history")
class UserQuizHistoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    val quiz: QuizEntity,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "quiz_answer_id")
    val quizAnswer: QuizAnswerEntity,

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_mission_history_id")
    val missionHistory: UserMissionHistoryEntity,

    @Column(name = "answer")
    val answer: String,
) : BaseEntity()
