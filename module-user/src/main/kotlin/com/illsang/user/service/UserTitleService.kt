package com.illsang.user.service

import com.illsang.common.enums.TitleGrade
import com.illsang.common.enums.TitleType
import com.illsang.user.domain.entity.UserTitleEntity
import com.illsang.user.domain.model.UserTitleModel
import com.illsang.user.repository.UserTitleRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserTitleService(
    private val userTitleRepository: UserTitleRepository,
) {

    fun findById(id: String): UserTitleEntity {
        return userTitleRepository.findByIdOrNull(id)
            ?: throw IllegalArgumentException("UserTitle not found with id: $id")
    }

    @Transactional
    fun evaluateUserTitleByQuest(
        userId: String,
        maxStreak: Int
    ): UserTitleModel {
        val userTitle = userTitleRepository.findByUserId(userId)

        val titleType: TitleType = TitleType.CONTRIBUTION


        val (titleCodes, titleGrade) = when {
            maxStreak >= 360 -> listOf("TQ00011") to TitleGrade.LEGEND
            maxStreak >= 240 -> listOf("TQ00010") to TitleGrade.RARE
            maxStreak >= 120 -> listOf("TQ00009") to TitleGrade.RARE
            maxStreak >= 60  -> listOf("TQ00008") to TitleGrade.RARE
            maxStreak >= 30  -> listOf("TQ00007") to TitleGrade.STANDARD
            maxStreak >= 14  -> listOf("TQ00006") to TitleGrade.STANDARD
            maxStreak >= 7   -> listOf("TQ00005") to TitleGrade.STANDARD
            maxStreak >= 4   -> listOf("TQ00004") to TitleGrade.STANDARD
            maxStreak >= 1   -> listOf("TQ00003") to TitleGrade.STANDARD
            else -> emptyList<String>() to TitleGrade.STANDARD
        }

        // 3. 새로운 타이틀만 추가
        titleCodes.forEach { code ->
            userTitle.createTitle(titleType, titleGrade, code)
        }

        return UserTitleModel.from(userTitle)
    }

}
