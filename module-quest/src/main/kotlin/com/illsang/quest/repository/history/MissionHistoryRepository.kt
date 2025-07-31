package com.illsang.quest.repository.history

import com.illsang.quest.domain.entity.history.UserMissionHistoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MissionHistoryRepository : JpaRepository<UserMissionHistoryEntity, Long>
