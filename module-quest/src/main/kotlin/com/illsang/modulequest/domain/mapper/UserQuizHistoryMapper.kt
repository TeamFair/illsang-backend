package com.illsang.modulequest.domain.mapper

import com.illsang.modulequest.adapter.out.persistence.entity.QuizEntity
import com.illsang.modulequest.adapter.out.persistence.entity.UserMissionHistoryEntity
import com.illsang.modulequest.adapter.out.persistence.entity.UserQuizHistoryEntity
import com.illsang.modulequest.application.command.CreateUserQuizHistoryCommand
import com.illsang.modulequest.application.command.UpdateUserQuizHistoryCommand
import com.illsang.modulequest.domain.model.UserQuizHistoryModel

object UserQuizHistoryMapper {
    fun toModel(entity: UserQuizHistoryEntity): UserQuizHistoryModel {
        return UserQuizHistoryModel(
            id = entity.id,
            userId = entity.userId,
            answer = entity.answer,
            submittedAt = entity.submittedAt,
            quizId = entity.quiz.id!!,
            userMissionHistoryId = entity.userMissionHistory.id!!,
            createdBy = entity.createdBy,
            createdAt = entity.createdAt,
            updatedBy = entity.updatedBy,
            updatedAt = entity.updatedAt
        )
    }

    fun toEntity(
        model: UserQuizHistoryModel,
        quizEntity: QuizEntity,
        userMissionHistoryEntity: UserMissionHistoryEntity
    ): UserQuizHistoryEntity {
        return UserQuizHistoryEntity(
            id = model.id,
            userId = model.userId,
            answer = model.answer,
            submittedAt = model.submittedAt,
            quiz = quizEntity,
            userMissionHistory = userMissionHistoryEntity
        ).apply {
            createdBy = model.createdBy
            createdAt = model.createdAt
            updatedBy = model.updatedBy
            updatedAt = model.updatedAt
        }
    }

    fun toModel(command: CreateUserQuizHistoryCommand): UserQuizHistoryModel {
        return UserQuizHistoryModel(
            userId = command.userId,
            answer = command.answer,
            submittedAt = command.submittedAt,
            quizId = command.quizId,
            userMissionHistoryId = command.userMissionHistoryId
        )
    }

    fun toModel(command: UpdateUserQuizHistoryCommand, existing: UserQuizHistoryModel): UserQuizHistoryModel {
        return existing.copy(
            answer = command.answer ?: existing.answer,
            submittedAt = command.submittedAt ?: existing.submittedAt
        )
    }
}
