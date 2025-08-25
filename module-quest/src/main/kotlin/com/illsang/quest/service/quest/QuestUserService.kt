package com.illsang.quest.service.quest

import com.illsang.quest.dto.request.quest.QuestUserBannerRequest
import com.illsang.quest.dto.request.quest.QuestUserRequest
import com.illsang.quest.dto.request.quest.QuestUserTypeRequest
import com.illsang.quest.dto.response.user.*
import com.illsang.quest.enums.MissionType
import com.illsang.quest.enums.QuestType
import com.illsang.quest.repository.quest.QuestRepository
import com.illsang.quest.service.user.MissionHistoryService
import com.illsang.quest.service.user.QuestHistoryService
import com.illsang.quest.service.user.UserQuestFavoriteService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class QuestUserService(
    private val questRepository: QuestRepository,
    private val questHistoryService: QuestHistoryService,
    private val missionHistoryService: MissionHistoryService,
    private val userQuestFavoriteService: UserQuestFavoriteService,
) {

    fun findAllPopular(userId: String, commercialAreaCode: String, pageable: Pageable): Page<QuestUserPopularResponse> {
        val questRequest = QuestUserRequest(userId = userId, commercialAreaCode = commercialAreaCode, popularYn = true)
        val quests = this.findUserQuest(questRequest, pageable)

        return quests.map { QuestUserPopularResponse.from(it) }
    }

    fun findAllRecommend(
        userId: String,
        commercialAreaCode: String,
        pageable: Pageable
    ): Page<QuestUserRecommendResponse> {
        val questRequest = QuestUserRequest(userId = userId, commercialAreaCode = commercialAreaCode)
        val quests = this.findUserQuest(questRequest, pageable)

        return quests.map { QuestUserRecommendResponse.from(it) }
    }

    fun findAllReward(userId: String, commercialAreaCode: String, pageable: Pageable): Page<QuestUserRewardResponse> {
        val questRequest =
            QuestUserRequest(userId = userId, commercialAreaCode = commercialAreaCode, orderRewardDesc = true)
        val quests = this.findUserQuest(questRequest, pageable)

        return quests.map { QuestUserRewardResponse.from(it) }
    }

    fun findAllType(
        userId: String,
        commercialAreaCode: String,
        request: QuestUserTypeRequest,
        pageable: Pageable
    ): Page<QuestUserTypeResponse> {
        val questRequest = QuestUserRequest(
            userId = userId,
            commercialAreaCode = commercialAreaCode,
            orderRewardDesc = request.orderRewardDesc,
            questType = request.questType,
            repeatFrequency = request.repeatFrequency,
            favoriteYn = request.favoriteYn,
            completedYn = request.completedYn,
            orderExpiredDesc = request.orderExpiredDesc,
        )

        val quests = this.findUserQuest(questRequest, pageable)
        val questFavorites =
            this.userQuestFavoriteService.findAllByQuestIdAndUserId(userId, quests.mapNotNull { it.id })

        return quests.map {
            QuestUserTypeResponse.from(
                quest = it,
                favorite = questFavorites.find { favorite -> favorite.questId == it.id }
            )
        }
    }

    fun findAllBanner(userId: String, bannerId: Long, request: QuestUserBannerRequest, pageable: Pageable): Page<QuestUserBannerResponse> {
        val questRequest = QuestUserRequest(
            userId = userId,
            orderExpiredDesc = request.orderExpiredDesc,
            orderRewardDesc = request.orderRewardDesc,
            bannerId = bannerId,
            completedYn = request.completedYn,
        )

        val quests = this.findUserQuest(questRequest, pageable)

        return quests.map { QuestUserBannerResponse.from(it) }
    }

    fun findQuestDetail(userId: String, questId: Long): QuestUserDetailResponse {
        val quest = (this.questRepository.findByIdOrNull(questId)
            ?: throw IllegalArgumentException("Cannot find quest by id: $questId"))


        val missionExampleImages = quest.missions
            .filter { it.type == MissionType.PHOTO }
            .flatMap { this.missionHistoryService.findLikeCountByMissionId(it.id!!) }

        var userRank : Int? = null
        if (QuestType.REPEAT == quest.type) {
            userRank = this.questHistoryService.findCustomerRank(userId, questId)
        }

        val questFavorite = this.userQuestFavoriteService.findAllByQuestIdAndUserId(userId, listOf(questId))

        return QuestUserDetailResponse.from(
            quest = quest,
            userRank = userRank,
            favoriteYn = questFavorite.isNotEmpty(),
            missionExampleImages = missionExampleImages,
        )
    }

    private fun findUserQuest(request: QuestUserRequest, pageable: Pageable) =
        this.questRepository.findAllUserQuest(request, pageable)

}
