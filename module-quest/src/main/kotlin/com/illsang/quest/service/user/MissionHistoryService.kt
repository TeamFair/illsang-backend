package com.illsang.quest.service.user

import com.illsang.auth.domain.model.AuthenticationModel
import com.illsang.common.event.management.image.ImageExistOrThrowEvent
import com.illsang.common.event.user.info.UserInfoGetEvent
import com.illsang.quest.domain.entity.user.UserMissionHistoryEmojiEntity
import com.illsang.quest.domain.entity.user.UserMissionHistoryEntity
import com.illsang.quest.domain.entity.user.UserQuizHistoryEntity
import com.illsang.quest.domain.model.user.ChallengeModel
import com.illsang.quest.dto.request.user.ChallengeCreateRequest
import com.illsang.quest.dto.response.user.MissionHistoryOwnerResponse
import com.illsang.quest.dto.response.user.MissionHistoryRandomResponse
import com.illsang.quest.enums.EmojiType
import com.illsang.quest.enums.MissionType
import com.illsang.quest.repository.user.MissionHistoryEmojiRepository
import com.illsang.quest.repository.user.MissionHistoryRepository
import com.illsang.quest.service.quest.MissionService
import com.illsang.quest.service.quest.QuizService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MissionHistoryService(
    private val quizService: QuizService,
    private val missionService: MissionService,
    private val questHistoryService: QuestHistoryService,
    private val missionHistoryRepository: MissionHistoryRepository,
    private val missionHistoryEmojiRepository: MissionHistoryEmojiRepository,
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

    fun findAllRandom(pageable: Pageable): Page<MissionHistoryRandomResponse> {
        val missionHistory = this.missionHistoryRepository.findAllRandom(MissionType.PHOTO, pageable)

        val usersEvent = UserInfoGetEvent(missionHistory.content.map { it.userId })
        this.eventPublisher.publishEvent(usersEvent)

        return missionHistory.map {
            MissionHistoryRandomResponse.from(
                missionHistory = it,
                userInfo = usersEvent.response.find { user -> it.userId == user.userId }!!
            )
        }
    }

    @Transactional
    fun increaseViewCount(missionHistoryId: Long, userId: String) {
        val missionHistoryEntity = this.findById(missionHistoryId)
        if (missionHistoryEntity.createdBy != userId) {
            missionHistoryEntity.increaseViewCount()
        }
    }

    @Transactional
    fun createEmoji(missionHistoryId: Long, userId: String, emojiType: EmojiType) {
        val missionHistory = this.findById(missionHistoryId)
        this.missionHistoryEmojiRepository.findByUserIdAndMissionHistoryAndType(userId, missionHistory, emojiType)
            ?.let { throw IllegalArgumentException("Already exists") }

        val emojiEntity =
            UserMissionHistoryEmojiEntity(missionHistory = missionHistory, userId = userId, type = emojiType)

        missionHistory.addEmoji(emojiEntity)
    }

    @Transactional
    fun deleteEmoji(missionHistoryId: Long, userId: String, emojiType: EmojiType) {
        val missionHistory = this.findById(missionHistoryId)
        val emoji =
            this.missionHistoryEmojiRepository.findByUserIdAndMissionHistoryAndType(userId, missionHistory, emojiType)
                ?: throw IllegalArgumentException("Not found")

        missionHistory.removeEmoji(emoji)
        this.missionHistoryEmojiRepository.delete(emoji)
    }

    fun findByUserId(userId: String, pageable: Pageable): Page<MissionHistoryOwnerResponse> {
        val missionHistories = this.missionHistoryRepository.findAllByUserId(userId, pageable)

        return missionHistories.map { MissionHistoryOwnerResponse.from(it) }
    }

    private fun findById(missionHistoryId: Long): UserMissionHistoryEntity =
        this.missionHistoryRepository.findByIdOrNull(missionHistoryId)
            ?: throw IllegalArgumentException("Mission History not found")

}
