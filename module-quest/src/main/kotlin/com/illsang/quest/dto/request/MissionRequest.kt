package com.illsang.quest.dto.request

import com.illsang.quest.domain.entity.MissionEntity
import com.illsang.quest.domain.entity.QuestEntity
import com.illsang.quest.enums.MissionType

data class MissionCreateRequest(
    val questId: Long,
    val type: MissionType,
    val title: String,
    val sortOrder: Int = 0,
    val quizzes: List<QuizCreateWithMissionRequest>? = null,
) {

    fun toEntity(quest: QuestEntity): MissionEntity {
        val mission = MissionEntity(
            type = type,
            title = title,
            sortOrder = sortOrder,
            quest = quest,
        )

        this.quizzes?.let { quizzes ->
            mission.addQuizzes(quizzes.map { it.toEntity(mission) })
        }

        return mission
    }

}

data class MissionUpdateRequest(
    val type: MissionType,
    val title: String,
    val sortOrder: Int = 0,
)
