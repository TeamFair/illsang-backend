package com.illsang.quest.domain.entity

import com.illsang.common.entity.BaseEntity
import com.illsang.quest.dto.request.QuizAnswerUpdateRequest
import jakarta.persistence.*

@Entity
@Table(name = "quiz_answer")
class QuizAnswerEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    var quiz: QuizEntity,

    @Column(nullable = false, columnDefinition = "TEXT")
    var answer: String,

    @Column(name = "sort_order")
    var sortOrder: Int = 0
) : BaseEntity() {

    fun update(request: QuizAnswerUpdateRequest) {
        this.answer = request.answer
        this.sortOrder = request.sortOrder
    }

}
