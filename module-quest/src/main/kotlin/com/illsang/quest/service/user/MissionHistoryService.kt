package com.illsang.quest.service.user

import com.illsang.auth.domain.model.AuthenticationModel
import com.illsang.common.enums.ResultCode
import com.illsang.common.event.management.image.ImageExistOrThrowEvent
import com.illsang.common.event.user.info.UserInfoGetEvent
import com.illsang.quest.domain.entity.user.UserMissionHistoryEmojiEntity
import com.illsang.quest.domain.entity.user.UserMissionHistoryEntity
import com.illsang.quest.domain.entity.user.UserQuizHistoryEntity
import com.illsang.quest.domain.model.user.ChallengeModel
import com.illsang.quest.dto.request.user.ChallengeCreateRequest
import com.illsang.quest.dto.response.user.MissionHistoryExampleResponse
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
    fun submitMission(
        request: ChallengeCreateRequest,
        authenticationModel: AuthenticationModel
    ): Pair<ResultCode, ChallengeModel?> {
        val mission = this.missionService.findById(request.missionId)

        val quizAndAnswer = if (mission.type.requireQuiz()) {
            val quizId = requireNotNull(request.quizId) { "Quiz Id not found" }
            val answerText = requireNotNull(request.answer) { "Answer not found" }

            val quiz = this.quizService.findById(quizId)
            val answer = quiz.findQuizAnswer(answerText)
                ?: return Pair(ResultCode.INCORRECT_QUIZ, null)
            quiz to answer
        } else {
            request.imageId?.let {
                this.eventPublisher.publishEvent(ImageExistOrThrowEvent(it))
            } ?: throw IllegalArgumentException("Mission Image not found")

            null
        }

        // TODO 반복퀘스트 이미 처리된 이력 있는지

        val questHistory = this.questHistoryService.findOrCreate(authenticationModel.userId, mission.quest)
        val missionHistory = UserMissionHistoryEntity(
            userId = authenticationModel.userId,
            mission = mission,
            questHistory = questHistory,
            submitImageId = request.imageId,
        )
        questHistory.addMissionHistory(missionHistory)


        if (mission.type.requireQuiz()) {
            val (quiz, quizAnswer) = quizAndAnswer!!
            val quizHistory = UserQuizHistoryEntity(
                quiz = quiz,
                quizAnswer = quizAnswer,
                missionHistory = missionHistory,
                answer = request.answer!!,
            )
            missionHistory.addQuizHistory(quizHistory)
        }

        this.questHistoryService.complete(questHistory)

        return Pair(ResultCode.SUCCESS, ChallengeModel.from(questHistory))
    }

    fun findLikeCountByMissionId(missionId: Long) =
        this.missionHistoryRepository.findTop3ByMissionIdAndStatusInOrderByLikeCountDesc(missionId)

    fun findAllRandom(userId: String, pageable: Pageable): Page<MissionHistoryRandomResponse> {
        val missionHistory = this.missionHistoryRepository.findAllRandom(MissionType.PHOTO, pageable)

        val usersEvent = UserInfoGetEvent(missionHistory.content.map { it.userId })
        this.eventPublisher.publishEvent(usersEvent)

        val userEmojiHistory =
            this.missionHistoryEmojiRepository.findByUserIdAndMissionHistoryIn(userId, missionHistory.content)

        return missionHistory.map {
            MissionHistoryRandomResponse.from(
                missionHistory = it,
                userInfo = usersEvent.response.find { user -> it.userId == user.userId }!!,
                userEmojiHistory = userEmojiHistory,
            )
        }
    }

    fun exampleMissionHistory(
        missionId: Long,
        userId: String,
        pageable: Pageable
    ): Page<MissionHistoryExampleResponse> {
        val missionHistory =
            this.missionHistoryRepository.findAllRandomByMissionId(MissionType.PHOTO, missionId, pageable)

        val usersEvent = UserInfoGetEvent(missionHistory.content.map { it.userId })
        this.eventPublisher.publishEvent(usersEvent)

        val userEmojiHistory =
            this.missionHistoryEmojiRepository.findByUserIdAndMissionHistoryIn(userId, missionHistory.content)

        return missionHistory.map {
            MissionHistoryExampleResponse.from(
                missionHistory = it,
                userInfo = usersEvent.response.find { user -> it.userId == user.userId }!!,
                userEmojiHistory = userEmojiHistory,
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
        val missionHistories = this.missionHistoryRepository.findAllByMissionTypeAndUserIdAndStatusIn(MissionType.PHOTO, userId, pageable)

        return missionHistories.map { MissionHistoryOwnerResponse.from(it) }
    }

    @Transactional
    fun reportMissionHistory(missionHistoryId: Long) {
        val missionHistoryEntity = this.findById(missionHistoryId)
        missionHistoryEntity.report()
    }

    @Transactional
    fun deleteMissionHistory(missionHistoryId: Long, userId: String) {
        val missionHistoryEntity = this.findById(missionHistoryId)
        if (missionHistoryEntity.userId != userId) {
            throw IllegalArgumentException("Mission User not equal")
        }

        this.missionHistoryRepository.delete(missionHistoryEntity)
    }

    private fun findById(missionHistoryId: Long): UserMissionHistoryEntity =
        this.missionHistoryRepository.findByIdOrNull(missionHistoryId)
            ?: throw IllegalArgumentException("Mission History not found")

}
