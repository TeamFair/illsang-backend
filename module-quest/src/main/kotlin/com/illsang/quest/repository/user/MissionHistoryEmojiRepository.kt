package com.illsang.quest.repository.user

import com.illsang.quest.domain.entity.user.UserMissionHistoryEmojiEntity
import com.illsang.quest.domain.entity.user.UserMissionHistoryEntity
import com.illsang.quest.enums.EmojiType
import com.illsang.quest.enums.MissionType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface MissionHistoryEmojiRepository : JpaRepository<UserMissionHistoryEmojiEntity, Long> {
    fun findByUserIdAndMissionHistoryAndType(
        userId: String,
        missionHistory: UserMissionHistoryEntity,
        emojiType: EmojiType
    ): UserMissionHistoryEmojiEntity?
}
