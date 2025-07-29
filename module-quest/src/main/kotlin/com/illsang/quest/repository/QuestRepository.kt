package com.illsang.quest.repository

import com.illsang.quest.domain.entity.QuestEntity
import org.springframework.data.jpa.repository.JpaRepository

interface QuestRepository : JpaRepository<QuestEntity, Long>
