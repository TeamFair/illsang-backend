package com.teamfair.illsang.core.quest.repository

import com.teamfair.illsang.core.quest.entity.Quest
import org.springframework.data.jpa.repository.JpaRepository

interface QuestRepository: JpaRepository<Quest, Long> {
}
