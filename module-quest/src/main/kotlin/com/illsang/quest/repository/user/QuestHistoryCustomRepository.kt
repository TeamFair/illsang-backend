package com.illsang.quest.repository.user

import com.illsang.quest.domain.entity.user.UserQuestHistoryEntity
import java.time.LocalDateTime

interface QuestHistoryCustomRepository {
    fun findMaxConsecutiveDays(userId: String): Int
    fun findAllUserRankByStore(startDate: LocalDateTime, endDate: LocalDateTime, storeId: Long?) : List<String>
    fun findAllUserRankByArea(startDate: LocalDateTime, endDate: LocalDateTime, metroAreaCodes: List<String?>) : List<String>
    fun findFirstByUserIdAndQuestIdOrderByCompletedAtDesc(userId: String, questIds: List<Long>): List<UserQuestHistoryEntity>?
}