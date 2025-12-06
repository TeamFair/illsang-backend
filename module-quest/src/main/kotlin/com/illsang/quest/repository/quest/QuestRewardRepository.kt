package com.illsang.quest.repository.quest

import com.illsang.quest.domain.entity.quest.QuestRewardEntity
import com.illsang.quest.enums.RewardType
import org.springframework.data.jpa.repository.JpaRepository

interface QuestRewardRepository : JpaRepository<QuestRewardEntity, Long>{
    fun findFirstByQuestIdAndRewardType(questId: Long, rewardType: RewardType): QuestRewardEntity
}
