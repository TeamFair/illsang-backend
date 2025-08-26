package com.illsang.user.service

import com.illsang.common.enums.TitleGrade
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
) {

    fun findById(id: String): UserTitleEntity {
        return userTitleRepository.findByIdOrNull(id)
            ?: throw IllegalArgumentException("UserTitle not found with id: $id")
    }

    fun findAllByUserId(userId: String): List<UserTitleModel> {
        val userTitles = userTitleRepository.findAllByUserId(userId)
        return userTitles.map { UserTitleModel.from(it) }
    }

    @Transactional
    fun evaluateUserTitleByQuest(
        userId: String,
        maxStreak: Int
    ): List<UserTitleModel> {
       val userTitles = userTitleRepository.findAllByUserId(userId)
        return userTitles.map { UserTitleModel.from(it) }
    }


}
