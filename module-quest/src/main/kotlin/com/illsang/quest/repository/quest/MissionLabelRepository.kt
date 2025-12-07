package com.illsang.quest.repository.quest

import com.illsang.quest.domain.entity.quest.MissionLabelEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MissionLabelRepository : JpaRepository<MissionLabelEntity, Long>
