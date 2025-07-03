package com.illsang.modulequest.adapter.out.persistence.entity

import com.illsang.common.adapter.out.persistence.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "quiz")
class QuizEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id", nullable = false)
    var mission: MissionEntity,

    @Column(nullable = false, columnDefinition = "TEXT")
    var question: String,

    @Column(columnDefinition = "TEXT")
    var hint: String? = null,

    @Column(name = "sort_order")
    var sortOrder: Int = 0,

    @OneToMany(mappedBy = "quiz", cascade = [CascadeType.ALL], orphanRemoval = true)
    val answers: MutableList<QuizAnswerEntity> = mutableListOf(),

    @OneToMany(mappedBy = "quiz", cascade = [CascadeType.ALL])
    val userHistories: MutableList<UserQuizHistoryEntity> = mutableListOf()
) : BaseEntity() {

    fun addAnswer(answer: QuizAnswerEntity) {
        answers.add(answer)
        answer.quiz = this
    }
}
