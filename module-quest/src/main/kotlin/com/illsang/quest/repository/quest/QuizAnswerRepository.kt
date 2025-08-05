package com.illsang.quest.repository.quest

import com.illsang.quest.domain.entity.quest.QuizAnswerEntity
import org.springframework.data.jpa.repository.JpaRepository

interface QuizAnswerRepository : JpaRepository<QuizAnswerEntity, Long>
