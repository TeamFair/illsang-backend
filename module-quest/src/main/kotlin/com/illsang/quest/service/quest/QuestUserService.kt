package com.illsang.quest.service.quest

import com.illsang.quest.dto.request.quest.QuestUserRequest
import com.illsang.quest.dto.response.quest.QuestUserPopularResponse
import com.illsang.quest.dto.response.quest.QuestUserRecommendResponse
import com.illsang.quest.dto.response.quest.QuestUserRewardResponse
import com.illsang.quest.repository.quest.QuestRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class QuestUserService(
    private val questRepository: QuestRepository,
) {

    fun findAllPopular(userId: String, commercialAreaCode: String, pageable: Pageable): Page<QuestUserPopularResponse> {
        val questRequest = QuestUserRequest(userId = userId, commercialAreaCode = commercialAreaCode, popularYn = true)
        val quests = findUncompletedQuest(questRequest, pageable)

        return quests.map { QuestUserPopularResponse.from(it) }
    }

    fun findAllRecommend(userId: String, commercialAreaCode: String, pageable: Pageable): Page<QuestUserRecommendResponse> {
        val questRequest = QuestUserRequest(userId = userId, commercialAreaCode = commercialAreaCode)
        val quests = findUncompletedQuest(questRequest, pageable)

        return quests.map { QuestUserRecommendResponse.from(it) }
    }

    fun findAllReward(userId: String, commercialAreaCode: String, pageable: Pageable): Page<QuestUserRewardResponse> {
        val questRequest = QuestUserRequest(userId = userId, commercialAreaCode = commercialAreaCode, orderReward = true)
        val quests = findUncompletedQuest(questRequest, pageable)

        return quests.map { QuestUserRewardResponse.from(it) }
    }

    private fun findUncompletedQuest(request: QuestUserRequest, pageable: Pageable) =
        this.questRepository.findAllUncompletedQuest(request, pageable)

}
