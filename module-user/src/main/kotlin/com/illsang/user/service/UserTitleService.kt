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
        titleId: String,
        titleName: String,
        titleGrade: TitleGrade,
        titleType: TitleType,
    ) {
        val user = userRepository.findByIdOrNull(userId)
            ?: throw IllegalArgumentException("User not found with id: $userId")

        val existingTitles = userTitleRepository.existsByUserIdAndTitleId(userId, titleId)
        if(!existingTitles) {
            val newTitle = UserTitleEntity(
                user = user,
                titleId = titleId,
                titleName = titleName,
                titleGrade = titleGrade,
                titleType = titleType
            )
            userTitleRepository.save(newTitle)
            user.updateTitle(newTitle)
        }

    }


}
