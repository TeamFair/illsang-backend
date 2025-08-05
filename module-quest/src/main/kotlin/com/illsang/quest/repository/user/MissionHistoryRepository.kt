package com.illsang.quest.repository.user

import com.illsang.quest.domain.entity.user.UserMissionHistoryEntity
import com.illsang.quest.enums.MissionType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface MissionHistoryRepository : JpaRepository<UserMissionHistoryEntity, Long> {
    fun findTop3ByMissionIdOrderByLikeCountDesc(questId: Long): List<UserMissionHistoryEntity>
    @Query("""
        SELECT umh
        FROM UserMissionHistoryEntity AS umh
        INNER JOIN FETCH umh.mission AS m
        INNER JOIN FETCH m.quest AS q
        WHERE m.type = :missionType
        ORDER BY (CASE WHEN umh.likeCount = 0 THEN 0 ELSE 1 END) DESC
    """)
    fun findAllRandom(missionType: MissionType, pageable: Pageable): Page<UserMissionHistoryEntity>
}
