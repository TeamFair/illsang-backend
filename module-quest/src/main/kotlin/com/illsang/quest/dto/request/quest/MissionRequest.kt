package com.illsang.quest.dto.request.quest

import com.illsang.quest.domain.entity.quest.MissionEntity
import com.illsang.quest.domain.entity.quest.QuestEntity
import com.illsang.quest.enums.MissionType

data class MissionCreateRequest(
    val questId: Long,
    val title: String,
    val type: MissionType,
    val sortOrder: Int = 0,
    val quizzes: List<QuizCreateWithMissionRequest>? = null,
    val labels: List<MissionLabelCreateWithMissionRequest>? = null,
) {

    fun toEntity(quest: QuestEntity): MissionEntity {
        val mission = MissionEntity(
            type = type,
            sortOrder = sortOrder,
            quest = quest,
            title = title,
        )

        this.quizzes?.let { quizzes ->
            mission.addQuizzes(quizzes.map { it.toEntity(mission) })
        }
        this.labels?.let { labels ->
            mission.addMissions(labels.map { it.toEntity(mission) })
        }

        return mission
    }

}

data class MissionUpdateRequest(
    val title: String,
    val type: MissionType,
    val sortOrder: Int = 0,
)
