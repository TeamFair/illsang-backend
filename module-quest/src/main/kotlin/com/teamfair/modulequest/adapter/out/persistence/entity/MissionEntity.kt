package com.teamfair.modulequest.adapter.out.persistence.entity

import com.illsang.common.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "mission")
class MissionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quest_id", nullable = false)
    var quest: QuestEntity,

    @Column(nullable = false)
    var title: String,

    @Column(columnDefinition = "TEXT")
    var description: String? = null,

    @Column(name = "sort_order")
    var sortOrder: Int = 0,

    @OneToMany(mappedBy = "mission", cascade = [CascadeType.ALL], orphanRemoval = true)
    val quizzes: MutableList<QuizEntity> = mutableListOf(),

    @OneToMany(mappedBy = "mission", cascade = [CascadeType.ALL])
    val userHistories: MutableList<UserMissionHistoryEntity> = mutableListOf()
) : BaseEntity() {

    fun addQuiz(quiz: QuizEntity) {
        quizzes.add(quiz)
        quiz.mission = this
    }
} 