package com.illsang.user.repository


import com.illsang.user.domain.entity.UserEmojiEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserEmojiRepository : JpaRepository<UserEmojiEntity, Long> {
    fun findByUserId(userId: Long): List<UserEmojiEntity>
}
