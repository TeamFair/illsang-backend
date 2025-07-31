package com.illsang.quest.repository.quest

import com.illsang.quest.domain.entity.quest.QuizEntity
import org.springframework.data.jpa.repository.JpaRepository

interface QuizRepository : JpaRepository<QuizEntity, Long>
