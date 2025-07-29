package com.illsang.quest.repository

import com.illsang.quest.domain.entity.QuizAnswerEntity
import org.springframework.data.jpa.repository.JpaRepository

interface QuizAnswerRepository : JpaRepository<QuizAnswerEntity, Long>
