package com.illsang.quest.repository.history

import com.illsang.quest.domain.entity.history.UserQuestHistoryEntity
import com.illsang.quest.domain.entity.quest.QuestEntity
import org.springframework.data.jpa.repository.JpaRepository

interface QuestHistoryRepository : JpaRepository<UserQuestHistoryEntity, Long> {
    fun findByUserIdAndQuest(userId: String, quest: QuestEntity): UserQuestHistoryEntity?
}
