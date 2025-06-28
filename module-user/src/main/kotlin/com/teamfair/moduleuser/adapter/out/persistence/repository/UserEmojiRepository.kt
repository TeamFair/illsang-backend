package com.teamfair.moduleuser.adapter.out.persistence.repository


import com.teamfair.moduleuser.adapter.out.persistence.entity.UserEmojiEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserEmojiRepository : JpaRepository<UserEmojiEntity, Long> {
    fun findByUserId(userId: Long): List<UserEmojiEntity>
}