package com.illsang.quest.domain.entity.quest

import com.illsang.common.entity.BaseEntity
import com.illsang.quest.dto.request.quest.QuizUpdateRequest
import jakarta.persistence.*

@Entity
@Table(name = "quiz")
class QuizEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    var mission: MissionEntity,

    @Column(name = "question")
    var question: String,

    @Column(name = "hint")
    var hint: String? = null,

    @Column(name = "sort_order")
    var sortOrder: Int = 0,

    @OneToMany(mappedBy = "quiz", cascade = [CascadeType.ALL], orphanRemoval = true)
    val answers: MutableList<QuizAnswerEntity> = mutableListOf(),
) : BaseEntity() {

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

    fun findQuizAnswer(answer: String): QuizAnswerEntity? {
        return this.answers.findLast { it.answer == answer }
    }

}
