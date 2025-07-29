package com.illsang.quest.repository

import com.illsang.quest.domain.entity.QuizEntity
import org.springframework.data.jpa.repository.JpaRepository

interface QuizRepository : JpaRepository<QuizEntity, Long>
