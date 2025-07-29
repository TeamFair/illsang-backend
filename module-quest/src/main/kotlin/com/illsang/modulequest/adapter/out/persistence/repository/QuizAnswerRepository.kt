package com.illsang.modulequest.adapter.out.persistence.repository

import com.illsang.modulequest.adapter.out.persistence.entity.QuizAnswerEntity
import org.springframework.data.jpa.repository.JpaRepository

interface QuizAnswerRepository : JpaRepository<QuizAnswerEntity, Long> {
    fun findByQuizId(quizId: Long): List<QuizAnswerEntity>
}
