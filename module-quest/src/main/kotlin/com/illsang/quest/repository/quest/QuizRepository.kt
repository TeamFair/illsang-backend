package com.illsang.quest.repository.quest

import com.illsang.quest.domain.entity.quest.QuizEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface QuizRepository : JpaRepository<QuizEntity, Long> {
    @Query(value = "SELECT q FROM QuizEntity q WHERE q.mission.id = :missionId ORDER BY RAND() LIMIT 1")
    fun findRandomByMissionId(missionId: Long): QuizEntity?
}
