package com.teamfair.modulequest.adapter.out.persistence.repository

import com.teamfair.modulequest.adapter.out.persistence.entity.QuizAnswerEntity
import org.springframework.data.jpa.repository.JpaRepository

interface QuizAnswerRepository : JpaRepository<QuizAnswerEntity, Long> 