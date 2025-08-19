package com.illsang.quest.repository.user

import com.illsang.quest.domain.entity.user.UserMissionHistoryEmojiEntity
import com.illsang.quest.domain.entity.user.UserMissionHistoryEntity
import com.illsang.quest.enums.EmojiType
import org.springframework.data.jpa.repository.JpaRepository

interface MissionHistoryEmojiRepository : JpaRepository<UserMissionHistoryEmojiEntity, Long> {
    fun findByUserIdAndMissionHistoryAndType(
        userId: String,
        missionHistory: UserMissionHistoryEntity,
        emojiType: EmojiType
    ): UserMissionHistoryEmojiEntity?

    fun findByUserIdAndMissionHistoryIn(
        userId: String,
        missionHistory: List<UserMissionHistoryEntity>,
    ): List<UserMissionHistoryEmojiEntity>
}
