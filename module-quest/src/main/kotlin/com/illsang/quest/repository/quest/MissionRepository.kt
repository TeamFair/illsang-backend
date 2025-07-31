package com.illsang.quest.repository.quest

import com.illsang.quest.domain.entity.quest.MissionEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MissionRepository : JpaRepository<MissionEntity, Long>
