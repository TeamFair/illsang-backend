package com.illsang.quest.repository.user

import com.illsang.quest.domain.entity.quest.QuestEntity
import com.illsang.quest.domain.entity.user.UserQuestHistoryEntity
import com.illsang.quest.enums.QuestHistoryStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface QuestHistoryRepository : JpaRepository<UserQuestHistoryEntity, Long> {
    fun findByUserIdAndQuest(userId: String, quest: QuestEntity): UserQuestHistoryEntity?
    @Query("""
    SELECT r.rank FROM (
        SELECT uh.userId as userId, 
               DENSE_RANK() OVER (ORDER BY COUNT(uh) DESC) as rank
        FROM UserQuestHistoryEntity uh 
        WHERE uh.quest.id = :questId AND uh.status = :questHistoryStatus
        GROUP BY uh.userId
    ) r 
    WHERE r.userId = :userId
""")
    fun findCustomerRank(userId: String, questId: Long, questHistoryStatus: QuestHistoryStatus = QuestHistoryStatus.COMPLETE): Int?
}
