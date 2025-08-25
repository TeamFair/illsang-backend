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
    fun evaluateUserTitle(
        userId: String,
        titleType: TitleType = TitleType.METRO,
        titleGrade: TitleGrade = TitleGrade.STANDARD,
    ): UserTitleModel {
        val userTitle = userTitleRepository.findByUserId(userId)
        userTitle.changeTitle(titleType, titleGrade)
        return UserTitleModel.from(userTitle)
    }

}
