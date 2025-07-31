package com.illsang.quest.repository.quest

import com.illsang.quest.domain.entity.quest.QuestRewardEntity
import org.springframework.data.jpa.repository.JpaRepository

interface QuestRewardRepository : JpaRepository<QuestRewardEntity, Long>
