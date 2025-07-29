package com.illsang.quest.repository

import com.illsang.quest.domain.entity.QuestRewardEntity
import org.springframework.data.jpa.repository.JpaRepository

interface QuestRewardRepository : JpaRepository<QuestRewardEntity, Long>
