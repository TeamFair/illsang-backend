package com.illsang.quest.repository.user

interface QuestHistoryCustomRepository {
    fun findMaxConsecutiveDays(userId: String): Int
}