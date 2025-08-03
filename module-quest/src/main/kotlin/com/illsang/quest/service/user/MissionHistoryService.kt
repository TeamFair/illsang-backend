package com.illsang.quest.service.user

import com.illsang.auth.domain.model.AuthenticationModel
import com.illsang.common.event.management.image.ImageExistOrThrowEvent
import com.illsang.quest.domain.entity.user.UserMissionHistoryEntity
import com.illsang.quest.domain.entity.user.UserQuizHistoryEntity
import com.illsang.quest.domain.model.history.ChallengeModel
import com.illsang.quest.dto.request.user.ChallengeCreateRequest
import com.illsang.quest.repository.user.MissionHistoryRepository
import com.illsang.quest.service.quest.MissionService
import com.illsang.quest.service.quest.QuizService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MissionHistoryService(
    private val quizService: QuizService,
    private val missionService: MissionService,
    private val questHistoryService: QuestHistoryService,
    private val missionHistoryRepository: MissionHistoryRepository,
    private val eventPublisher: ApplicationEventPublisher,
) {

    @Transactional
    fun submitMission(request: ChallengeCreateRequest, authenticationModel: AuthenticationModel): ChallengeModel {
        val mission = this.missionService.findById(request.missionId)
        val questHistory = this.questHistoryService.findOrCreate(authenticationModel.userId, mission.quest)
        val missionHistory = UserMissionHistoryEntity(
            userId = authenticationModel.userId,
            mission = mission,
            questHistory = questHistory,
            submitImageId = request.imageId,
        )
        questHistory.addMissionHistory(missionHistory)

        // TODO 반복퀘스트 이미 처리된 이력 있는지

        if (!mission.type.requireQuiz()) {
            request.imageId?.let {
                this.eventPublisher.publishEvent(ImageExistOrThrowEvent(it))
            } ?: throw IllegalArgumentException("Mission Image not found")
        } else {
            requireNotNull(request.quizId, { "Quiz Id not found" })
            requireNotNull(request.answer, { "Answer not found" })

            val quiz = this.quizService.findById(request.quizId)
            val answer = quiz.findQuizAnswer(request.answer)
                ?: throw IllegalArgumentException("정답이 아닙니다.")
            val quizHistory = UserQuizHistoryEntity(
                quiz = quiz,
                quizAnswer = answer,
                missionHistory = missionHistory,
                answer = request.answer,
            )
            missionHistory.addQuizHistory(quizHistory)
        }

        this.questHistoryService.complete(questHistory)

        return ChallengeModel.from(questHistory)
    }

    fun findLikeCountByMissionId(questId: Long) =
        this.missionHistoryRepository.findTop3ByMissionIdOrderByLikeCountDesc(questId)

}
