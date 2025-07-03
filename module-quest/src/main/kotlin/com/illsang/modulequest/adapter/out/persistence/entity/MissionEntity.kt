package com.illsang.modulequest.adapter.out.persistence.entity

import com.illsang.common.adapter.out.persistence.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "mission")
class MissionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, length = 50)
    var type: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quest_id", nullable = false)
    var quest: QuestEntity,

    @Column(nullable = false)
    var title: String,

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
