package com.teamfair.modulequest.adapter.out.persistence.repository

import com.teamfair.modulequest.adapter.out.persistence.entity.QuizEntity
import org.springframework.data.jpa.repository.JpaRepository

interface QuizRepository : JpaRepository<QuizEntity, Long> 