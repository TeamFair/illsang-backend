package com.teamfair.modulequest.adapter.out.persistence.repository

import com.teamfair.modulequest.adapter.out.persistence.entity.QuestRewardEntity
import org.springframework.data.jpa.repository.JpaRepository

interface QuestRewardRepository : JpaRepository<QuestRewardEntity, Long> 