package com.illsang.moduleuser.adapter.out.persistence.repository


import com.illsang.moduleuser.adapter.out.persistence.entity.UserEmojiEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserEmojiRepository : JpaRepository<UserEmojiEntity, Long> {
    fun findByUserId(userId: Long): List<UserEmojiEntity>
}
