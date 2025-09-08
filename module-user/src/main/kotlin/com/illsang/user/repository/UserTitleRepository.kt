package com.illsang.user.repository

import com.illsang.common.enums.TitleGrade
import com.illsang.user.domain.entity.UserTitleEntity
import org.springframework.data.domain.Page
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.domain.Pageable

interface UserTitleRepository : JpaRepository<UserTitleEntity, Long>{
    fun findAllByUserId(userId: String): List<UserTitleEntity>
    fun existsByUserIdAndTitleId(userId: String, titleId: String): Boolean
    fun findByUserIdAndId(userId: String, id: Long): UserTitleEntity?
    fun findByUserIdIn(userIds: List<String>): List<UserTitleEntity>
    fun findAllByUserIdAndReadYnIsFalse(userId: String): List<UserTitleEntity>
    fun findAllByTitleGradeAndTitleId(pageable: Pageable, titleGrade: TitleGrade, titleId: String?): Page<UserTitleEntity>

}
