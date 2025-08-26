package com.illsang.user.service

import com.illsang.common.enums.TitleGrade
import com.illsang.common.enums.TitleInfo
import com.illsang.common.enums.TitleType
import com.illsang.user.domain.entity.UserEntity
import com.illsang.user.domain.entity.UserTitleEntity
import com.illsang.user.domain.model.UserTitleModel
import com.illsang.user.repository.UserRepository
import com.illsang.user.repository.UserTitleRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserTitleService(
    private val userTitleRepository: UserTitleRepository,
    private val userRepository: UserRepository,
) {

    fun findById(id: Long): UserTitleEntity {
        return userTitleRepository.findByIdOrNull(id)
            ?: throw IllegalArgumentException("UserTitle not found with id: $id")
    }

    fun getTitlesByUserId(userId: String): List<UserTitleModel> {
        val userTitles = userTitleRepository.findAllByUserId(userId)
        return userTitles.map { UserTitleModel.from(it) }
    }

    fun getTitle(id: Long): UserTitleModel{
        val userTitle = this.findById(id)
        return UserTitleModel.from(userTitle)
    }

    @Transactional
    fun evaluateUserTitleByQuest(
        userId: String,
        maxStreak: Int
    ): List<UserTitleModel> {
        val user = userRepository.findByIdOrNull(userId)
            ?: throw IllegalArgumentException("User not found with id: $userId")

        // 1. 이미 유저가 가지고 있는 칭호 조회
        val existingTitles = userTitleRepository.findAllByUserId(userId)
        val ownedTitleCodes = existingTitles.map { it.titleId }.toSet()

        // 2. 연속일 기준 달성 가능한 타이틀 필터링
        val achievableTitles = TitleInfo.entries.filter { title ->
            when (title) {
                TitleInfo.TITLE_360_DAYS -> maxStreak >= 360
                TitleInfo.TITLE_TWO_FORTY_DAYS -> maxStreak >= 240
                TitleInfo.TITLE_ONE_TWENTY_DAYS -> maxStreak >= 120
                TitleInfo.TITLE_SIXTY_DAYS -> maxStreak >= 60
                TitleInfo.TITLE_THIRTY_DAYS -> maxStreak >= 30
                TitleInfo.TITLE_FOURTEEN_DAYS -> maxStreak >= 14
                TitleInfo.TITLE_SEVEN_DAYS -> maxStreak >= 7
                TitleInfo.TITLE_FOUR_DAYS -> maxStreak >= 4
                TitleInfo.TITLE_ONE_DAY -> maxStreak >= 2
                else -> false
            }
        }

        // 3. 새로운 타이틀만 추가
        val newTitles = mutableListOf<UserTitleEntity>()
        achievableTitles.forEach { titleEnum ->
            if (ownedTitleCodes.contains(titleEnum.titleId).not()) {
                val userTitle = UserTitleEntity(
                    user = user,
                    titleId = titleEnum.titleId,
                    titleName = titleEnum.titleName,
                    titleGrade = titleEnum.titleGrade,
                    titleType = titleEnum.titleType
                )
                newTitles.add(userTitle)
            }
        }

        // 4. DB 저장
        userTitleRepository.saveAll(newTitles)

        // 5. DTO 변환 후 반환
        return (existingTitles + newTitles).map { UserTitleModel.from(it) }
    }


}
