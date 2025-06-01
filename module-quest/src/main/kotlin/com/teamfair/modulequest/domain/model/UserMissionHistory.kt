package com.teamfair.modulequest.domain.model

import com.teamfair.modulequest.domain.model.enums.MissionStatus
import java.time.LocalDateTime

data class UserMissionHistory(
    val id: Long? = null,
    val userId: Long,
    var status: MissionStatus = MissionStatus.PENDING,
    var submissionImageUrl: String? = null,
    var submittedAt: LocalDateTime? = null,
    val quizHistories: MutableList<UserQuizHistory> = mutableListOf(),
    val createdBy: String? = null,
    val createdAt: String? = null,
    val updatedBy: String? = null,
    val updatedAt: String? = null
) 