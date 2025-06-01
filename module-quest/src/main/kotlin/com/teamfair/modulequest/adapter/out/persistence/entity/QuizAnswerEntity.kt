package com.teamfair.modulequest.adapter.out.persistence.entity

import com.illsang.common.entity.BaseEntity
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
) : BaseEntity() 