package com.illsang.quest.repository

import com.illsang.quest.domain.entity.MissionEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MissionRepository : JpaRepository<MissionEntity, Long>
