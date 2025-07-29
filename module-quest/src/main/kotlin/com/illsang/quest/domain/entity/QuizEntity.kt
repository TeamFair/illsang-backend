package com.illsang.quest.domain.entity

import com.illsang.common.entity.BaseEntity
import com.illsang.quest.dto.request.QuizUpdateRequest
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
) : BaseEntity() {

    init {
        if (this.answers.isEmpty()) {
            throw IllegalArgumentException("There are no answers for this quiz")
        }
    }

    fun addAnswer(answers: List<QuizAnswerEntity>) {
       this.answers.addAll(answers)
    }

    fun update(request: QuizUpdateRequest) {
        this.question = request.question
        this.hint = request.hint
        this.sortOrder = request.sortOrder
    }

    fun deleteQuizAnswer(quizAnswerId: Long) {
        if (this.answers.size > 1) {
            this.answers.removeIf { it.id == quizAnswerId }
        } else
            throw IllegalArgumentException("Cannot remove a quiz answer from the quiz. Require at least one quiz answer.")
    }

}
